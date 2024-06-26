
# Current Supported Minecraft Version: 1.21

## GeoExtras
This is a Minecraft Spigot plugin that adds features we use on the MCNSA community server. It can be used alongside my other plugin or standalone.

# Modules
All the content in this plugin is separated into different distinct modules. These modules can be disabled by finding the module you want to disable in the config file and setting it to false.

## Artifacts
### Magnets
Holding a valid magnet item in your offhand will pull items and xp orbs to you when you move. Sneaking will disable the magnet effect (this is configurable).

#### Magnet Strengths:
Weak: Default pickup range of 2 blocks
Strong: Default pickup range of 4 blocks

#### Valid Magnet Item Types:
Weak: Rabbit Foot, Heart of the Sea, Ghast Tear, Nautilus Shell
Strong: Enchanted Golden Apple (Notch Apple), Nether Star, Netherite Ingot

### Zoomies
Having a valid zoomies item in your inventory will give you a speed boost while sprinting. Regular walking will not get a boost.

#### Zoomies Strengths:
Weak: Speed 1
Strong: Speed 2
Giga: Speed 5

#### Valid Zoomies Item Types:
Weak: Feather
Strong: Blaze Powder
Giga: Powered Rail

### Commands
| Command | Permission Node | Description |
|----|----|----|
| /geoartifact | GeoExtras.geoartifact.geoartifact | Lists artifact types |
| /geoartifact (magnet/zoomies) | GeoExtras.geoartifact.geoartifact | Add magnet lore to main hand item if valid material |
| /geoartifact (magnet/zoomies) (player) | GeoExtras.geoartifacts.staff | Spawns the given artifact type on the target player |
|  | GeoExtras.artifacts.magnet.weak | Gives access to use the weak magnets |
|  | GeoExtras.artifacts.magnet.strong | Gives access to use the strong magnets |
|  | GeoExtras.artifacts.zoomies.weak | Gives access to use the weak zoomies |
|  | GeoExtras.artifacts.zoomies.strong | Gives access to use the strong zoomies |
|  | GeoExtras.artifacts.zoomies.giga | Gives access to use the giga zoomies |

## Farm
Includes a couple different features relating to farming or plants.

### Shear Scythe
Allows the player to left or right click with shears in main or offhand to clear grass in a 9x9(configurable) area centered on where they click.

### Right Click Harvest
Allows right click harvesting and auto-replanting of the crops listed below.

#### Supported Crops:
- Wheat
- Potatoes
- Carrots
- Beetroot
- Nether Wart

### Bonemeal Other Things
Allows bonemealing to duplicate lily pads and all small flowers except wither roses.

## Chat Commands
Adds a couple silly chat commands.

### Commands
| Command | Permission Node | Description | Alias(es) |
|----|----|----|----|
| /heart (name) | GeoExtras.chat.heart | Shows your love for everyone or the target name | /love |
| /rng (max) (count) | GeoExtras.chat.rng | Generate a 1-20 with no arguments, or a given number of numbers with given maximum | /dice |
| /blamegeo | GeoExtras.chat.blamegeo | Assigns blame | /blame, /geo |
| /poggers | GeoExtras.chat.poggers | Poggers | /pog |
|  | GeoExtras.chat.poggers.others | Poggers on others | /pog |

## Silly Module
Misc features that are goofy or don't work properly

### Explode
Kills the player who used the command. They go out with a bang.

### Mount Teleports
In theory makes it so if you teleport while riding a mount the mount is tp'ed with you. Does not functoin properly and can potentially un-teleport you.

### Jank Step
In theory adds step assist like modded by teleporting the playe rup the block they are "walking" up. In practice only seems to do anything when swimming or near water. Still WIP.

### Piss Creepers
If you play the creeper explosion priming noise in a rapid loop say from a command block it kind of sounds like someone peeing. This adds yellow fireworks to their explosion to accomodate the sound.


# All Permission Nodes as a Tree
- GeoExtras.*
    - GeoExtras.artifacts.*
        - GeoExtras.artifacts.magnet.weak -- Allows usage of weak magnets
        - GeoExtras.artifacts.magnet.strong -- Allows usage of strong magnets
        - GeoExtras.artifacts.speed.giga -- Allows usage of giga speed items
        - GeoExtras.artifacts.speed.strong -- Allows usage of strong speed items
        - GeoExtras.artifacts.speed.weak -- Allows usage of weak speed items
        - GeoExtras.artifacts.staff -- Allows spawning of geoartifacts
    - GeoExtras.chat.*
        - GeoExtras.commands.heart -- Allows usage of /heart
        - GeoExtras.commands.rng -- Allows usage of /rng
        - GeoExtras.commands.blamegeo -- Allows usage of /blamegeo
        - GeoExtras.commands.poggers -- Allows usage of /poggers
        - GeoExtras.commands.poggers.others -- Allows usage of /poggers on others
    - GeoExtras.farm.*
        - GeoExtras.farm.harvest -- Allows usage of right click harvest
        - GeoExtras.farm.frow -- Allows usage of the extra bonemeal growth
        - GeoExtras.farm.scythe -- Allows usage of the shear scythe
    - GeoExtras.silly.*
        - GeoExtras.silly.mountTeleport -- Allows mounts to teleport with the player
        - GeoExtras.silly.jankStep -- Allows jank step to activate
        - GeoExtras.silly.explode -- Allows usage of /explode
    - GeoExtras.geoextras.*
        - GeoExtras.geoextras -- Allows use of /geoextras
        - GeoExtras.geoextras.reload -- Allows reload of config file