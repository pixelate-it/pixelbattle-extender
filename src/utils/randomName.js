module.exports = function randomName() {
    return Math.random().toString(36).slice(2);
}