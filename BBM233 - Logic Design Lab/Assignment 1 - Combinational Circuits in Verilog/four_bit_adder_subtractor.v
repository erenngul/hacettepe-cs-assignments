module four_bit_adder_subtractor(A, B, subtract, Result, Cout);
    input [3:0] A;
    input [3:0] B;
    input subtract;
    output [3:0] Result;
    output Cout;
    wire [3:0] ComplementB; // Result of TC
    wire [3:0] MUXResult; // Result of MUX

    // Used previous modules for structural design with explicit association
    two_s_complement TC(.In(B), .Out(ComplementB));
    four_bit_2x1_mux MUX(.In_1(B), .In_0(ComplementB), .Select(~subtract), .Out(MUXResult));
    four_bit_rca RCA(.A(A), .B(MUXResult), .Cin(1'b0), .S(Result), .Cout(Cout));

endmodule
