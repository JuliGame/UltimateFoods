# UltimateFoods

UltimateFoods is a powerful and flexible Minecraft plugin that allows server administrators to create custom food items with unique properties, effects, and crafting recipes.

## Features

- Create custom food items with unique properties
- Configure food saturation, hunger, and health restoration values
- Add custom crafting recipes
- Support for custom textures and models
- Integration with ItemsAdder and ItemBridge
- Economy support through Vault
- Intuitive in-game menu system for food management
- Permission-based access control
- Category-based organization of food items

## Dependencies

- Bukkit/Spigot 1.13+
- Vault (optional - for economy support)
- ItemsAdder (optional - for custom item support)
- ItemBridge (optional - for cross-plugin item support)

## Commands

- `/uf help` - Shows available commands
- `/uf create [category] [name]` - Creates a new food item
- `/uf get [food] [amount] [player]` - Gives food items
- `/uf menu` - Opens the food management menu
- `/uf reload` - Reloads the plugin configuration
- `/uf edit [food]` - Opens the food editor menu

## Permissions

- `uf.reload` - Allows reloading the plugin
- `uf.get` - Allows getting food items
- `uf.create` - Allows creating new food items
- `uf.menu` - Allows accessing the menu
- `uf.edit` - Allows editing food items
- `uf.edit.op` - Allows advanced editing options

## Libraries

The plugin includes two custom libraries:

### JuliMenus
A powerful menu system for creating interactive GUIs with support for:
- Pagination
- Button system
- Layered menus
- Menu navigation

### JConfig
A JSON configuration system that provides:
- Easy object serialization/deserialization
- Custom field exclusion
- UTF-8 encoding support
- Automatic file management

## Installation

1. Download the latest release
2. Place the jar in your plugins folder
3. Start/restart your server
4. Configure the plugin through the generated config files

## Configuration

Food items are stored in JSON format under the `plugins/UltimateFoods/categories/` directory. Each category contains food items with their respective properties.
