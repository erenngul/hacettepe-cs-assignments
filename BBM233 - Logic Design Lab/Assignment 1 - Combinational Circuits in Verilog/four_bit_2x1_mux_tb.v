`timescale 1ns/10ps
module four_bit_2x1_mux_tb;
	// Declaring inputs as regs
	reg [3:0] In_1;
	reg [3:0] In_0;
	reg Select;
	// Declaring output as wire
	wire [3:0] Out;
	// Declaring integers to use in for loops
	integer i;
	integer j;
	integer k;
	// Declaring binary numbers as regs to test all cases of inputs
	reg selectCount = 0;
	reg [3:0] count1 = 4'b0000;
	reg [3:0] count0 = 4'b0000;

	four_bit_2x1_mux UUT(In_1, In_0, Select, Out); // Instantiate UUT

	initial begin // Generating stimuli
		$dumpfile("result1.vcd");
      	$dumpvars;
      	for (i = 0; i < 2; i++) begin
			Select = selectCount;
			selectCount += 1;
			for (j = 0; j < 16; j++) begin
				// Assign count1 to In_1
				{In_1[3], In_1[2], In_1[1], In_1[0]} = count1;
				count1 += 1;
				for (k = 0; k < 16; k++) begin
					// Assign count0 to In_0
					{In_0[3], In_0[2], In_0[1], In_0[0]} = count0;
					count0 += 1;
					#10;
				end
				count0 = 0;
				#10;
			end
			count1 = 0;
			#10;
		end
      	$finish;
	end
	
endmodule
