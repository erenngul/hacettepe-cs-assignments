module counter_d(input reset, input clk, input mode, output [2:0] count);

    // present_state[2] = A
    // present_state[1] = B
    // present_state[0] = C
    reg [2:0] present_state = 2'b00;
    // next_state[2] = D_A
    // next_state[1] = D_B
    // next_state[0] = D_C
    wire [2:0] next_state;

    // Used D flip flops in this part for structural design with explicit association
    dff_sync_res D_A(.D((present_state[1] & ~present_state[0] & mode) | (~present_state[2] & present_state[1] & present_state[0] & ~mode) | (present_state[2] & ~present_state[1] & ~mode) | (present_state[2] & present_state[0] & mode) | (present_state[2] & ~present_state[0] & ~mode)), .clk(clk), .sync_reset(reset), .Q(next_state[2]));

    dff_sync_res D_B(.D((present_state[1] & ~present_state[0]) | (~present_state[1] & present_state[0] & ~mode) | (~present_state[2] & present_state[0] & mode)), .clk(clk), .sync_reset(reset), .Q(next_state[1]));

    dff_sync_res D_C(.D((~present_state[2] & ~present_state[1] & mode) | (~present_state[0] & ~mode) | (present_state[2] & present_state[1] & mode)), .clk(clk), .sync_reset(reset), .Q(next_state[0]));

    // Whenever there is a next state, update present state
    always @(next_state) begin
        present_state <= next_state;
    end

    // Assign present state to output count
    assign count[2] = present_state[2];
    assign count[1] = present_state[1];
    assign count[0] = present_state[0];

endmodule