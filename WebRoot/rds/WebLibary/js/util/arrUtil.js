Array.prototype.uniqueStrArr = function() {
    var o = new Object();
    for (var i = 0,j = 0; i < this.length; i++) {
        if (typeof o[this[i]] == 'undefined') {
            o[this[i]] = j++;
        }
    }
    this.length = 0;
    for (var key in o) {
        this[o[key]] = key;
    }
    return this;
}
