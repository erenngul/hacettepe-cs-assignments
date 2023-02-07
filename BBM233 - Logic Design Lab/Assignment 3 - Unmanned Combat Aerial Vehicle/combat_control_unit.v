`timescale 1us/1ns

module combat_control_unit(
    input rst,
    input track_target_command, 
    input clk, 
    input radar_echo, 
    input fire_command,
    output [13:0] distance_to_target, 
    output trigger_radar_transmitter, 
    output launch_missile,
    output [1:0] TTU_state,
    output [1:0] WCU_state,
    output [3:0] remaining_missiles
);

    // Wire variable to hold the output of target_locked of target_tracking_unit module
    wire target_locked;

    // Instantiated target_tracking_unit module
    target_tracking_unit ttu(.rst(rst), .track_target_command(track_target_command), .clk(clk), .echo(radar_echo), .trigger_radar_transmitter(trigger_radar_transmitter), .distance_to_target(distance_to_target), .target_locked(target_locked), .TTU_state(TTU_state));

    // Instantiated weapons_control_unit module
    weapons_control_unit wcu(.target_locked(target_locked), .clk(clk), .rst(rst), .fire_command(fire_command), .launch_missile(launch_missile), .remaining_missiles(remaining_missiles), .WCU_state(WCU_state));

endmodule