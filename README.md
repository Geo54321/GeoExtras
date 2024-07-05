
# Current Supported Minecraft Version: 1.21

## GeoExtras
This is a Minecraft Spigot plugin that adds features we use on the MCNSA community server. It can be used alongside my other plugin or standalone.

# Modules
All the content in this plugin is separated into different distinct modules. These modules can be disabled by finding the module you want to disable in the config file and setting it to false.

## Artifacts Module
### Magnets
Holding a valid magnet item in your offhand will pull items and xp orbs to you when you move. Sneaking will disable the magnet effect (this is configurable).

#### Magnet Strengths:
- Weak: Default pickup range of 2 blocks
- Strong: Default pickup range of 4 blocks

#### Valid Magnet Item Types:
- Weak: Rabbit Foot, Heart of the Sea, Ghast Tear, Nautilus Shell
- Strong: Enchanted Golden Apple (Notch Apple), Nether Star, Netherite Ingot

### Zoomies
Having a valid zoomies item in your inventory will give you a speed boost while sprinting. Regular walking will not get a boost.

#### Zoomies Strengths:
- Weak: Speed 1
- Strong: Speed 2
- Giga: Speed 5

#### Valid Zoomies Item Types:
- Weak: Feather
- Strong: Blaze Powder
- Giga: Powered Rail

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

## Farm Module
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

## Misc Module
Contains misc features that don't fit into other categories

### Lapis Elevators
Allows the use to make "elevators" that teleport the user between vertical lapis blocks as long as there is an space above the elevator for them.

**Permission Node: GeoExtras.misc.elevators**

## Chat Module
Adds a couple silly chat commands.

### Commands
| Command | Permission Node | Description | Alias(es) |
|----|----|----|----|
| /heart (name) | GeoExtras.chat.heart | Shows your love for everyone or the target name | /love |
| /rng (max) (count) | GeoExtras.chat.rng | Generate a 1-20 with no arguments, or a given number of numbers with given maximum | /dice |
| /blamegeo | GeoExtras.chat.blamegeo | Assigns blame | /blame, /geo |
| /poggers | GeoExtras.chat.poggers | Poggers | /pog |
| /poogers (player) | GeoExtras.chat.poggers.others | Poggers on others | /pog |

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

## Enchantment Module
Adds custom enchants

### Forge
Smelt the drops of specific block types when broken with a tool with this enchantment.

- Only usable on pickaxes
- Conflicts with Fortune and Silk Touch enchantments

#### Smeltable blocks:
- Copper and Deepslate Copper Ores
- Iron and Deepslate Iron Ores
- Gold and Deepslate Gold Ores
- Ancient Debris
- Nether Gold Ore - Drops whole gold ingot instead

### Hewing (WIP)
Chops down the entire tree when break a log that is part of a tree.

- Only usable on axes
- No conflicts with other enchantments
- Breaks up to 120 connected blocks (there is a config option for this)

### Illumination (WIP)
While holding an item with this enchant if you walk into a space with minimum block lighting, it will automatically place a torch from your inventory at your feet.

- Only usable on shields
- No conflicts with other enchantments

### Death Woven (WIP)
When you die with an item with the Death Woven enchantment in your inventory it will stay in your inventory when you respawn.

- Usable on any item type
- No conflicts with other enchantments

### Drain (WIP)
When you hit an enemy with an item with this enchantment it will heal 1 heart of damage.

- Usable on swords
- No conflicts with other enchantments

### Quarrying (WIP)
When you break a block with an item with the enchantment it will also break all 8 adjacent block at the same time. This functions similarly to hammers in modded minecraft.

- Usable on pickaxes and shovels
- No conflicts with other enchantments

### Paring (WIP)
When you break leaves with an item with this enchantment it will break all other leaves of the same type with a 5x5x5 centered on the originally broken leaf.

- Usable on axes and hoes
- No conflicts with other enchantments

### Faithful (WIP)
When you teleport while riding a mount with this enchantment the mount will teleport with you.

- Usable on horse armor
- No conflicts with other enchantments


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
    - GeoExtras.misc.*
        - GeoExtras.misc.elevators -- Allows Usage of elevators
    - GeoExtras.farm.*
        - GeoExtras.farm.harvest -- Allows usage of right click harvest
        - GeoExtras.farm.frow -- Allows usage of the extra bonemeal growth
        - GeoExtras.farm.scythe -- Allows usage of the shear scythe
    - GeoExtras.silly.*
        - GeoExtras.silly.mountTeleport -- Allows mounts to teleport with the player
        - GeoExtras.silly.jankStep -- Allows jank step to activate
        - GeoExtras.silly.explode -- Allows usage of /explode
    - GeoExtras.magic.enchants.*
        - GeoMagic.magic.enchants.forge -- Allows usage of forge enchant
        - GeoMagic.magic.enchants.hewing -- Allows usage of hewing enchant
        - GeoMagic.magic.enchants.illumination -- Allows usage of illumination enchant
        - GeoMagic.magic.enchants.deathwoven -- Allows usage of deathwoven enchant
        - GeoMagic.magic.enchants.drain -- Allows usage of drain enchant
        - GeoMagic.magic.enchants.quarrying -- Allows usage of quarrying enchant
        - GeoMagic.magic.enchants.paring -- Allows usage of paring enchant
        - GeoMagic.magic.enchants.faithful -- Allows usage of faithful enchant
    - GeoExtras.magic.*
        - GeoExtras.magic.enchants.*
        - GeoExtras.magic.command
    - GeoExtras.geoextras.*
        - GeoExtras.geoextras -- Allows use of /geoextras
        - GeoExtras.geoextras.reload -- Allows reload of config file