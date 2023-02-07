`timescale 1ns/1ps
module four_bit_adder_subtractor_tb;
    // Declaring inputs as regs
    reg [3:0] A, B;
    reg subtract;
    // Declaring outputs as wires
    wire [3:0] Result;
    wire Cout;
    // Declaring integers to use in for loops
    integer i;
    integer j;
    integer k;
    // Declaring binary numbers as regs to test all cases of inputs
    reg [3:0] countA = 4'b0000;
    reg [3:0] countB = 4'b0000;

    // Instantiate UUT
    four_bit_adder_subtractor UUT(.A(A), .B(B), .subtract(subtract), .Result(Result), .Cout(Cout));
    
    initial begin // Generating stimuli
        $dumpfile("result4.vcd");
        $dumpvars;
        for (i = 0; i < 2; i++) begin
            subtract = i;
            for (j = 0; j < 16; j++) begin
                // Assign countA to input A
                {A[3], A[2], A[1], A[0]} = countA;
                countA += 1;
                for (k = 0; k < 16; k++) begin
                    // Assign countB to input B
                    {B[3], B[2], B[1], B[0]} = countB;
                    countB += 1;
                    #10;
                end
                countB = 0;
                #10;
            end
            countA = 0;
            #10;
        end
        $finish;
    end

endmodule
