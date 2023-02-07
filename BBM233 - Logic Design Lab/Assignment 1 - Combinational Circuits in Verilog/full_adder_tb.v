`timescale 1 ns/10 ps
module full_adder_tb;
    reg A, B, Cin; // Declaring inputs as regs
    wire S, Cout; // Declaring outputs as wires
    integer i; // Declaring integer to use in for loop
    reg [3:0] count = 4'b0000; // Declaring binary number as reg to test all cases of inputs

    full_adder UUT(A, B, Cin, S, Cout); // Instantiate UUT

    initial begin // Generating stimuli
        $dumpfile("result2.vcd");
        $dumpvars;
        for (i = 0; i < 8; i++) begin
            // Assign count to all inputs
            {A, B, Cin} = count;
            count += 1;
            #10;
        end
        $finish;
    end

endmodule