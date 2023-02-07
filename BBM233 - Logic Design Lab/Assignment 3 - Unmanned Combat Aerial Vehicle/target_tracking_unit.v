`timescale 1us/1ns

module target_tracking_unit(
    input rst,
    input track_target_command,
    input clk,
    input echo,
    output reg trigger_radar_transmitter,
    output reg [13:0] distance_to_target,
    output reg target_locked,
    output [1:0] TTU_state
);

    // Parameters for states
    parameter idle = 2'b00, transmit = 2'b01, listen_for_echo = 2'b10, track = 2'b11;
    // Reg variables to hold temporary time and current state
    reg [31:0] previous_saved_time;
    reg [1:0] current_state = idle;

    // Always block to reset or update states and outputs
    always @(posedge clk or posedge rst) begin
        // If reset on posedge of rst, make everything 0 and change current state to idle
        if (rst == 1) begin
            trigger_radar_transmitter <= 0;
            distance_to_target <= 0;
            target_locked <= 0;
            current_state <= idle;
        end
        // On every posedge of clk, change states and outputs
        else begin
            
            // If target locked or echo is 1 and current state is not track, change current state to track
            if ((target_locked == 1 || echo == 1) && current_state != track) begin
                current_state <= track;
            end
            
            // If target locked is 1 and the clock cycled 30 times then change current state to idle and make target locked and distance to target 0
            else if (target_locked == 1) begin
                if ($stime - previous_saved_time >= 300) begin
                    current_state <= idle;
                    target_locked <= 0;
                    distance_to_target <= 0;
                end
            end

            // If trigger radar transmitter is 1 then change current state to transmit
            else if (trigger_radar_transmitter == 1) begin
                current_state <= transmit;
            end

            // If the clocked cycled 10 times then change current state to idle and make target locked and distance to target 0
            else if ($stime - previous_saved_time >= 100) begin
                current_state <= idle;
                target_locked <= 0;
                distance_to_target <= 0;
            end

            // If track target command is 0 and current state is transmit then change current state to listen for echo
            else if (track_target_command == 0 && current_state == transmit) begin
                current_state <= listen_for_echo;
            end
        end
    end

    // Assign current_state to TTU_state whenever it changes
    assign TTU_state = current_state;

    // Always block to compute additional outputs
    always @(posedge track_target_command or posedge echo) begin
        case(current_state)
            idle:
                if (track_target_command == 1 && trigger_radar_transmitter == 0) begin
                    trigger_radar_transmitter <= 1;
                    #50;
                    previous_saved_time <= $stime; // started transmit timer
                    trigger_radar_transmitter <= 0;
                end
            listen_for_echo:
                if (echo == 1) begin
                    distance_to_target <= ((3*100)*($stime - previous_saved_time))/2; // calculating distance to target
                    target_locked <= 1;
                    previous_saved_time <= $stime; // started target_update timer
                end
            track:
                if (track_target_command == 1) begin
                    trigger_radar_transmitter <= 1;
                    target_locked <= 0;
                    #50;
                    previous_saved_time <= $stime; // started transmit update timer
                    trigger_radar_transmitter <= 0;
                end
        endcase

    end

endmodule