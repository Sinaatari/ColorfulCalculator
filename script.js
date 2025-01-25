function appendToDisplay(value) {
    const display = document.getElementById('display');
    if (display.value === '0' && value !== '.') {
        display.value = value;
    } else {
        display.value += value;
    }
}

function clearDisplay() {
    document.getElementById('display').value = '0';
}

function calculate() {
    const display = document.getElementById('display');
    try {
        display.value = eval(display.value.replace('sqrt', 'Math.sqrt').replace('exp', 'Math.exp').replace('log', 'Math.log10').replace('pi', Math.PI));
    } catch (error) {
        display.value = 'Error';
    }
}
