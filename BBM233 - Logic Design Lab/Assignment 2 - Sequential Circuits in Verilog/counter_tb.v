`timescale 1ns/1ps

module counter_tb;
    reg reset, clk, mode; // Inputs
    wire [2:0] count; // Output
    integer i; // Integer for the for loop

    reg counter = 1'b0;
	
    // Instantiate UUTs
    counter_d uut(reset, clk, mode, count);
    counter_jk c1(reset, clk, mode, count);

    // Increment mode by 1
    initial begin
        for (i = 0; i < 2; i++) begin
            mode = counter;
            counter += 1;
            #122;
        end
        $finish;
    end

    // Update reset values
    initial begin
        $dumpvars;
        reset <= 1; #22;
        reset <= 0; #200;
        reset <= 1; #20;
        $finish;
    end

    initial begin // Generate clock
        clk = 0;
        forever begin
            #5;
            clk = ~clk; // Change every 10ns
        end
    end

endmodule