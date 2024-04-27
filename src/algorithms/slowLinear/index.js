const BaseAlgorithm = require('../BaseAlgorithm');
const progressBar = require('../../utils/progressBar');
const randomHex = require('../../utils/randomHex');

class slowLinear extends BaseAlgorithm {
    constructor(data, config, diff) { super(data, config, diff) };

    extend() {
        return new Array(this.config.NEW_CANVAS_X * this.config.NEW_CANVAS_Y).fill(0)
            .map((_, index) => {
                const pixel = this.data.find(pixel =>
                    (pixel.x === (index % this.config.NEW_CANVAS_X - this.diff[0]))
                    && (pixel.y === Math.floor(index / this.config.NEW_CANVAS_X - this.diff[1]))
                );

                const percent = Math.floor(index / (this.config.NEW_CANVAS_X * this.config.NEW_CANVAS_Y) * 100);
                process.stdout.write(`\r[+] [${progressBar(percent)}]: ${percent}%`);

                return {
                    x: index % this.config.NEW_CANVAS_X,
                    y: Math.floor(index / this.config.NEW_CANVAS_X),
                    color: pixel?.color ?? (this.config.COLOR ?? randomHex()),
                    author: pixel?.author ?? null,
                    tag: pixel?.tag ?? null
                }
            });
    }


}

module.exports = slowLinear;