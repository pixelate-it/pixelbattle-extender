const { config: dotenvConfig } = require('dotenv');

dotenvConfig({ path: '.env' });
module.exports = {
    NEW_CANVAS_X: Number(process.env.NEW_CANVAS_X),
    NEW_CANVAS_Y: Number(process.env.NEW_CANVAS_Y),
    OLD_CANVAS_X: Number(process.env.OLD_CANVAS_X),
    OLD_CANVAS_Y: Number(process.env.OLD_CANVAS_Y),
    DIFF: [0, 0],
    POSITION: Number(process.env.POSITION ?? 1),
    COLOR: process.env.CLR === 'RANDOM'
        ? null
        : (process.env.CLR ?? null),
    PIXELS_FILE: process.env.PIXELS_FILE,
    BENCHMARK: process.env.BENCHMARK === 'TRUE',
    ALGORITHM: Number(process.env.ALGORITHM),
    SAVE_TO: process.env.SAVE_TO
}