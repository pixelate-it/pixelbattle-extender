module.exports = function progressBar(current) {
    const width = 35;
    const position = (width * (current / 100)) | 0;
    return '='.repeat(position) + '-'.repeat((width - position) | 0);
}