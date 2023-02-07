`timescale 1us/1ns

module weapons_control_unit(
    input target_locked,
    input clk,
    input rst,
    input fire_command,
    output reg launch_missile,
    output [3:0] remaining_missiles,
    output [1:0] WCU_state
);

    // Parameters for states
    parameter idle = 2'b00, target_locked_state = 2'b01, fire = 2'b10, out_of_ammo = 2'b11;
    // Reg variables to hold current state and remaining missile count
    reg [1:0] current_state = idle;
    reg [3:0] remaining_missile_count = 4'b0100;

    // Always block to reset or update states and outputs
    always @(posedge clk or posedge rst) begin
        // If reset on posedge of rst, make launch missile 0, make remaining missile count 4 and change current state to idle
        if (rst == 1) begin
            remaining_missile_count = 4'b0100;
            launch_missile <= 0;
            current_state <= idle;
        end
        // On every posedge of clk, change states and outputs
        else begin
            
            // If current state is idle and target locked is 1 then change current state to target locked state
            if (current_state == idle && target_locked == 1) begin
                current_state <= target_locked_state;
            end

            // If current state is target locked state and target locked is 0 then change current state to idle
            else if (current_state == target_locked_state && target_locked == 0) begin
                current_state <= idle;
            end

            // If current state is target locked state and fire command is 1 then change current state to fire and make launch missile 1
            else if (current_state == target_locked_state && fire_command == 1) begin
                current_state <= fire;
                launch_missile <= 1;
            end

            // If current state is fire and target locked is 1 and remaining missiles are greater than zero then change current state to target locked state and make launch missile 0
            else if (current_state == fire && target_locked == 1 && remaining_missiles > 0) begin
                current_state <= target_locked_state;
                launch_missile <= 0;
            end

            // If current state is fire and target locked is 0 and remaining missiles are greater than zero then change current state to idle and make launch missile 0
            else if (current_state == fire && target_locked == 0 && remaining_missiles > 0) begin
                current_state <= idle;
                launch_missile <= 0;
            end

            // If current state is fire and there are not any missiles left then change current state to out of ammo and make launch missile 0
            else if (current_state == fire && remaining_missiles == 0) begin
                current_state <= out_of_ammo;
                launch_missile <= 0;
            end

        end
    end

    // Assign remaining missile count to remaining missiles whenever it changes
    assign remaining_missiles = remaining_missile_count;
    // Assign current state to WCU state whenever it changes
    assign WCU_state = current_state;

    // Always block to update remaining missile count
    always @(posedge launch_missile) begin
        // On the positive edge of launch missile, if remaining missile count is greater than zero then decrement the remaining missile count
        if (remaining_missile_count > 0)
            remaining_missile_count <= remaining_missile_count - 1;
    end

    // Always block to update current state and launch missile in the case when target locked becomes 0
    always @(negedge target_locked)begin
        launch_missile <= 0;
        current_state <= 2'b00;
    end

endmodule