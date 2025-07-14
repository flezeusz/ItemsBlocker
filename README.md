# ItemsBlocker

![Plugin Version](https://img.shields.io/github/v/tag/flezeusz/ItemsBlocker?label=Version&color=brightgreen)
![Downloads](https://img.shields.io/github/downloads/flezeusz/ItemsBlocker/total?label=Downloads&color=blueviolet)
![Paper Version](https://img.shields.io/badge/Paper-1.20.2+-blue.svg)

<a href="/#"><img src="https://raw.githubusercontent.com/intergrav/devins-badges/v2/assets/compact/supported/paper_46h.png" height="35"></a>
<a href="/#"><img src="https://raw.githubusercontent.com/intergrav/devins-badges/v2/assets/compact/supported/purpur_46h.png" height="35"></a>

## Description

**ItemsBlocker** is a Minecraft plugin that allows administrators to block specific items, enchantments, and potion effects. The plugin provides a set of commands to manage these restrictions.

## Features

* **Item Blocking:** Add/remove items from the blocked list.
* **Enchantment Blocking:** Block enchantments, optionally specifying a minimum level.
* **Potion Effect Blocking:** Block potion effects, optionally specifying a minimum level.
* **Netherite Crafting Blocking:** Enable/disable smithing of netherite items.

## Commands

* `/blocker` or `/itemsblocker` - Main command alias.  Requires the `itemsblocker.command` permission.

### Subcommands

* `/blocker help`: Displays the list of available commands.

#### Item Management

* `/blocker item add <item>`: Adds an item to the blocked list (e.g., `/blocker item add DIAMOND_SWORD`).
* `/blocker item remove <item>`: Removes an item from the blocked list.
* `/blocker item list`: Displays the list of blocked items.

#### Enchantment Management

* `/blocker enchantment add <enchantment_name> [level]`: Adds an enchantment to the blocked list (e.g., `/blocker enchantment add sharpness 2`).
    * `<enchantment_name>`: The name of the enchantment (use Minecraft names, e.g., `sharpness`, `efficiency`).
    * `[level]` (Optional): The minimum enchantment level to block. Default: 1.
* `/blocker enchantment remove <enchantment_name>`: Removes an enchantment from the blocked list.
* `/blocker enchantment list`: Displays the list of blocked enchantments and their levels.

#### Potion Effect Management

* `/blocker potion add <potion_effect_name> [level]`: Adds a potion effect to the blocked list (e.g., `/blocker potion add strength 2`).
    * `<potion_effect_name>`: The name of the potion effect (use Minecraft names, e.g., `speed`, `strength`).
    * `[level]` (Optional): The minimum potion effect level to block. Default: 1.
* `/blocker potion remove <potion_effect_name>`: Removes a potion effect from the blocked list.
* `/blocker potion list`: Displays the list of blocked potion effects and their levels.

#### Netherite Crafting Management

* `/blocker netherite <true|false>`: Enables/disables blocking of netherite item smithing.

## Permissions

* `itemsblocker.command`: Permission to use plugin commands.
* `itemsblocker.bypass`: Permission to bypass all item blocks.

## Libraries

* [Annotation Command Framework (ACF)](https://github.com/aikar/commands)
 * [Okaeri Configs](https://github.com/OkaeriPoland/okaeri-configs)

## Installation

1.  Download the the latest `.jar` file from [releases](https://github.com/flezeusz/ItemsBlocker/releases/latest).
2.  Place the `.jar` file in the `plugins` folder of your Minecraft server (Paper 1.20.2+).
3.  Restart the server.
