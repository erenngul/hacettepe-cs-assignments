`timescale 1us/1ns

module weapons_control_unit_tb;
    reg target_locked;
    reg clk;
    reg rst;
    reg fire_command;
    wire launch_missile;

    wire [1:0] WCU_state;
    wire [3:0] remaining_missiles;

    weapons_control_unit uut(target_locked, clk, rst, fire_command, launch_missile, remaining_missiles, WCU_state);

    initial begin
        $dumpfile("weapons_control_unit_result.vcd");
        $dumpvars;
        // Reset part
        rst = 1'b1;
        fire_command = 1'b0;
        target_locked = 1'b0;
        #17;
        rst = 1'b0;
        #3;
        // State changes to target_locked in the next posedge of clk
        target_locked = 1'b1;
        #20;
        // State changes to idle in the next posedge of clk
        target_locked = 1'b0;
        #20;
        // State changes to target_locked in the next posedge of clk
        target_locked = 1'b1;
        #20;
        // State changes to fire in the next posedge of clk
        fire_command = 1'b1;
        #10;
        // State changes to idle in the next posedge of clk
        target_locked = 1'b0;
        #20;
        // State changes to target_locked in the next posedge of clk
        target_locked = 1'b1;
        #110;
        $finish;
    end

    
    initial begin
        clk = 0;
        forever begin
            #5;
            clk = ~clk;
        end
    end

endmodule