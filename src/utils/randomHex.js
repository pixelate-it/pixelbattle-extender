module.exports = function randomHex(size = 6) {
    return '#' + [...Array(size)]
        .map(() =>
            Math.floor(Math.random() * 16)
                .toString(16)
        ).join('');
}