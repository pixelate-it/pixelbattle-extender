class BaseAlgorithm {
    config;
    constructor(data, config, diff) {
        this.data = data;
        this.config = config;
        this.diff = diff;
    }
}

module.exports = BaseAlgorithm;