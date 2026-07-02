# CopyEverything

A Minecraft 1.7.10 Forge mod that adds copy saplings and copy chests.

## Features

### Copy Sapling
- Crafted using 2x2 normal saplings
- When grown, uses the block below as the "leaves" material
- Grows a tree with a wooden trunk and a 3×3×3 crown made of the block below
- If placed on air, defaults to oak leaves
- Can be bonemealed to grow instantly

### Copy Chest
- Automatically copies items every 5 ticks
- Items multiply by 2 each time (1 → 2 → 4 → 8...)
- Each item type is limited to 768 total (12 stacks of 64, or 48 stacks of 16)
- 1024 inventory slots with pagination (54 slots per page)
- Standard double chest GUI appearance

## Crafting Recipes

### Copy Sapling
```
SS
SS
```
Where S = any sapling

### Copy Chest
No crafting recipe - obtain via creative mode or commands

## Building

### Requirements
- Java 8
- Gradle 2.0

### Build Command
```bash
./gradlew build
```

### Output
The mod jar will be in `build/libs/CopyEverything-1.0.jar`

## Development

### Setup Workspace
```bash
./gradlew setupDecompWorkspace eclipse
# or
./gradlew setupDecompWorkspace idea
```

### Run Client
```bash
./gradlew runClient
```

### Run Server
```bash
./gradlew runServer
```

## License

MIT License