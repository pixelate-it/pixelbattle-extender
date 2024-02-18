const before = Date.now();

const { writeFileSync } = require('fs');
const { config, formatDiff } = require('./config');

function progressBar(current) {
    const width = 35;
    const position = (width * (current / 100)) | 0;
    return '='.repeat(position) + '-'.repeat((width - position) | 0);
}

function randomHex(size = 6) {
    return '#' + [...Array(size)]
        .map(() => 
            Math.floor(Math.random() * 16)
            .toString(16)
        ).join('');
}

(async() => {
    if(config.OLD_CANVAS_X > config.NEW_CANVAS_X) throw new Error('[-] The old canvas X coordinate length should not be greater than the new one');
    if(config.OLD_CANVAS_Y > config.NEW_CANVAS_Y) throw new Error('[-] The old canvas Y coordinate length should not be greater than the new one');

    console.log('[+] Importing old canvas...');
    const pixels = await require(config.PIXELS_FILE);
    if(!pixels) throw new Error('[-] Path to pixels must be valid and file must exist');
    if(pixels.length !== (config.OLD_CANVAS_X * config.OLD_CANVAS_Y)) throw new Error('[-] The canvas you have given does not contain the same number of pixels as the given canvas in the config');

    console.log('[+] Formatting the differences...');
    formatDiff([config.NEW_CANVAS_X, config.NEW_CANVAS_Y], [config.OLD_CANVAS_X, config.OLD_CANVAS_Y]);

    console.log('[+] Painting pixels...');
    await writeFileSync(
        `canvas_${Math.random().toString(36).slice(2)}.json`, 
        JSON.stringify(
            new Array(config.NEW_CANVAS_X * config.NEW_CANVAS_Y).fill(0)
                .map((_, i) => {
                    const pixel = pixels.find(pix => (pix.x === (i % config.NEW_CANVAS_X - config.DIFF_X)) && (pix.y === Math.floor(i / config.NEW_CANVAS_X - config.DIFF_Y)));

                    const percent = Math.floor(i / (config.NEW_CANVAS_X * config.NEW_CANVAS_Y) * 100);
                    process.stdout.write(`\r[+] [${progressBar(percent)}]: ${percent}%`);

                    return {
                        x: i % config.NEW_CANVAS_X, 
                        y: Math.floor(i / config.NEW_CANVAS_X), 
                        color: pixel?.color ?? (config.COLOR ?? randomHex()), 
                        author: pixel?.author ?? null, 
                        tag: pixel?.tag ?? null 
                    }
                })
        )
    );

    process.stdout.write(`\r[+] [${progressBar(100)}]: 100%`);
    return console.log(`\n\n[+] Done. Took ${Date.now() - before}ms`);
})();