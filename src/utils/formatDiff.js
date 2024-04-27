module.exports = function formatDiff(position, NEW_CANVAS_LENGTH, OLD_CANVAS_LENGTH) {
    let diff;
    switch(position) {
        case 2: diff = [NEW_CANVAS_LENGTH[0] - OLD_CANVAS_LENGTH[0], 0]; break;
        case 3: diff = [0, NEW_CANVAS_LENGTH[1] - OLD_CANVAS_LENGTH[1]]; break;
        case 4: diff = [NEW_CANVAS_LENGTH[0] - OLD_CANVAS_LENGTH[0], 0]; break;
        case 5: diff = [(NEW_CANVAS_LENGTH[0] - OLD_CANVAS_LENGTH[0]) / 2, (NEW_CANVAS_LENGTH[1] - OLD_CANVAS_LENGTH[1]) / 2]; break;
        default: diff = [0, 0]; break;
    }

    return diff;
}