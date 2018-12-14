var app = new Vue({
    el: '#app',
    data: {
        message: 'Hello worldw!'
    },
    methods: {
        reverseMessage: function () {
            this.message = this.message.split('').reverse().join('')
        }
    }
});