# TODO

## Laser Beam Mod Plan

### IN-PROGRESS

### DONE
12. **Architecture Compliance Check**: Verified recent updates to `CONSTRAINTS.md` regarding Strict Architecture Rules (Entrypoints, Mixins, Event Registrations) and successfully verified `JujutsuMinecraftClient` exists and implements `ClientModInitializer`.
11. **Fix Gradle Environment**: Follow new AGENTS.md instructions. Revert to 1.20.1 and modify Veil/Satin API versions to correctly resolve from Modrinth/Ladysnake.
1. **Define Keybinds & Networking**: Register `G` key (default) for toggling the laser beam, and set up the C2S/S2C packets to sync laser state with the server.
2. **Setup Basic Veil Shader Registration**: Import Veil libraries and register a custom Veil renderer core for the laser beam to hook into the game rendering loop.
3. **Setup Basic Satin API Post-Processing Registration**: Initialize the Satin API to load and manage the Vignette and Blur post-processing shaders when the laser is active.
4. **Implement Raycasting & Core Variables**: Create the logic for starting the laser from the player's right hand and extending to the collision point of the look vector (render distance).
5. **Create Skeleton GLSL Shader Files**: Create the initial placeholder GLSL shader files (`laser_beam.vsh`, `laser_beam.fsh`) for Veil and Satin to recognize.
6. **Add the 3-layer Laser Core Rendering**: Implement the Core, Inline Outline, and Effect Layer rendering logic seamlessly unified in GLSL using Veil.
7. **Create Particle Systems (Beam, Impact, Decay)**: Implement the custom particle behaviors for phases 1, 2, and 3, capped at 500 particles.
8. **Implement Helix Spiral & Volumetric Bloom**: Add the DNA-like secondary orbiting particles and the semi-transparent bloom layers for the 'unstable energy' effect.
9. **Implement Block Heating Physics**: Handle the block heat state, realistic reddish-orange rendering overlay on hit blocks, and the 2-second linear fade out.
10. **Final Optimization & Stability Checks**: Handle crash catching, enforce the 5% frame time limit, verify 60 FPS on mid-specs, and test thoroughly.

### PLANNED
