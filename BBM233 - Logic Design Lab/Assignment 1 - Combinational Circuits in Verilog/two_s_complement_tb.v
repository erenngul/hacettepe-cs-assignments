`timescale 1ns/10ps
module two_s_complement_tb;
   // Declaring inputs as regs
   reg[3:0] In;
   reg[3:0] count = 4'b0000;
   // Declaring output as wire
   wire[3:0] Out;
   integer i; // Integer used in for loop
   
   two_s_complement UUT(In, Out); // Instantiate UUT

   initial begin // Generating stimuli
      $dumpfile("result0.vcd");
      $dumpvars;
      for (i = 0; i < 16; i++) begin
         {In[3], In[2], In[1], In[0]} = count;
         count += 1;
         #10;
      end
      $finish;
   end

endmodule 
