const { config: dotenvConfig } = require('dotenv');

dotenvConfig({ path: '.env' });

function formatDiff(NEW_CANVAS_LENGTH, OLD_CANVAS_LENGTH) {
    let diff;
    switch(cfg.POSITION) {
        case 2: diff = [NEW_CANVAS_LENGTH[0] - OLD_CANVAS_LENGTH[0], 0]; break;
        case 3: diff = [0, NEW_CANVAS_LENGTH[1] - OLD_CANVAS_LENGTH[1]]; break;
        case 4: diff = [NEW_CANVAS_LENGTH[0] - OLD_CANVAS_LENGTH[0], 0]; break;
        case 5: diff = [(NEW_CANVAS_LENGTH[0] - OLD_CANVAS_LENGTH[0]) / 2, (NEW_CANVAS_LENGTH[1] - OLD_CANVAS_LENGTH[1]) / 2]; break;
        default: diff = [0, 0]; break;
    }

    cfg.DIFF_X = diff[0];
    cfg.DIFF_Y = diff[1];

    return;
}

let cfg = {
    NEW_CANVAS_X: Number(process.env.NEW_CANVAS_X),
    NEW_CANVAS_Y: Number(process.env.NEW_CANVAS_Y),
    OLD_CANVAS_X: Number(process.env.OLD_CANVAS_X),
    OLD_CANVAS_Y: Number(process.env.OLD_CANVAS_Y),
    DIFF_X: [0, 0],
    DIFF_Y: [0, 0],
    POSITION: Number(process.env.POSITION ?? 5),
    COLOR: process.env.CLR === 'RANDOM' 
        ? null 
        : (process.env.CLR ?? null),
    PIXELS_FILE: process.env.PIXELS_FILE
}

//formatDiff([cfg.NEW_CANVAS_X, cfg.NEW_CANVAS_Y], [cfg.OLD_CANVAS_X, cfg.OLD_CANVAS_Y]);

module.exports = { config: cfg, formatDiff };