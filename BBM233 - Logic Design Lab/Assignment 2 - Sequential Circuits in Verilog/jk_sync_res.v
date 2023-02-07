module jk_sync_res(J, K, clk, sync_reset, Q);
    input J;
    input K;
    input clk;
    input sync_reset;
    output reg Q;

    // Change state at positive edge of clock
    always @(posedge clk)
    begin
        if(sync_reset==1'b1)
            Q <= 1'b0;
        else
            case ({J,K})
                2'b00 : Q <= Q;
                2'b01 : Q <= 1'b0;
                2'b10 : Q <= 1'b1;
                2'b11 : Q <= ~Q;
            endcase
    end

endmodule