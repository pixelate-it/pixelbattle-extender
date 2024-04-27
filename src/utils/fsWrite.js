const { writeFileSync } = require('fs');
const { join } = require('path');
const randomName = require('./randomName');

module.exports = async function fsWrite(path, data) {
    return writeFileSync(
        join(path, `canvas_${randomName()}.json`),
        JSON.stringify(data)
    );
}