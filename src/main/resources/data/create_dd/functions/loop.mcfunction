execute as @e[type=!player] store result score @s Health run data get entity @s Health

schedule function create_dd:loop 10t