module full_adder(
    input A,
    input B,
    input Cin,
    output S,
    output Cout
);

    // Assign values to outputs
    assign S = (A ^ B) ^ Cin;
    assign Cout = (A & B) | (B & Cin) | (Cin & A);

endmodule