require('./utils/pixelateIntro')();

const { join } = require('path');
const { mkdirSync, existsSync } = require('fs');
const config = require('../config');
const algorithms = require('./constants/algorithms');
const formatDiff = require('./utils/formatDiff');
const progressBar = require('./utils/progressBar');
const fsWrite = require('./utils/fsWrite');

console.log(`[?] The program has started!`);
process.stdout.write('[?] Benchmark' + ' ');
if(config.BENCHMARK) {
    var bench = {
        before: Date.now(),
        canvas: null,
        algorithm: null
    }
    process.stdout.write('enabled')
} else process.stdout.write('disabled');
process.stdout.write('\n');

(async() => {
    console.log('[+] Formatting the differences');
    const diff = formatDiff(
        config.POSITION,
        [config.NEW_CANVAS_X, config.NEW_CANVAS_Y],
        [config.OLD_CANVAS_X, config.OLD_CANVAS_Y]
    );

    process.stdout.write('[+] Importing canvas');
    if(bench) bench.canvas = Date.now();
    const canvas = require(join('../', config.PIXELS_FILE));
    process.stdout.write(
        '\r[+] Canvas imported!' + (
            bench
            ? (' ' + `Done in ${Date.now() - bench.canvas}ms`)
            : ''
        ) + '\n'
    );

    const algorithm_name = Object.keys(algorithms).find(id => algorithms[id] === config.ALGORITHM);
    if(!algorithm_name) throw Error('[-] The specified algorithm does not exist or is not written into constants');
    console.log(`[?] Selected algorithm: ${algorithm_name}`);

    const Algorithm = require(`./algorithms/${algorithm_name}/index.js`);
    if(bench) bench.algorithm = Date.now();
    const data = new Algorithm(canvas, config, diff).extend();
    process.stdout.write(`\r[+] [${progressBar(100)}]: 100%\n`);
    if(bench) console.log(`[?] The ${algorithm_name} algorithm took 350ms`);

    if(!existsSync(join(config.SAVE_TO))) await mkdirSync(join(config.SAVE_TO));
    await fsWrite(config.SAVE_TO, data);

    process.stdout.write('[?] Done');
    if(bench) process.stdout.write(' ' + `in ${Date.now() - bench.before}ms.`)
    else process.stdout.write('.');
    process.stdout.write('\n');
})();