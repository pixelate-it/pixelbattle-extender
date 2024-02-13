const before = Date.now();

const { ObjectId } = require('bson');
const { writeFileSync } = require('fs');
const { config, formatDiff } = require('./config');

(async() => {
    if(config.OLD_CANVAS_X > config.NEW_CANVAS_X) throw new Error('[-] The old canvas X coordinate length should not be greater than the new one');
    if(config.OLD_CANVAS_Y > config.NEW_CANVAS_Y) throw new Error('[-] The old canvas Y coordinate length should not be greater than the new one');

    console.log('[+] Importing old canvas...');
    const pixels = require(config.PIXELS_FILE);
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

                    return {
                        _id: { $oid: new ObjectId().toString() }, 
                        x: i % config.NEW_CANVAS_X, 
                        y: Math.floor(i / config.NEW_CANVAS_X), 
                        color: pixel?.color || config.COLOR, 
                        author: pixel?.author || null, 
                        tag: pixel?.tag || null 
                    }
                })
        )
    );

    return console.log(`[+] Done. Took ${Date.now() - before}ms`);
})();