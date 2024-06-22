const monthSelect = document.getElementById('month-select');
const yearSelect = document.getElementById('year-select');
const numberDays = document.querySelector('.number-days');
const prevMonthButton = document.getElementById('prev-month');
const nextMonthButton = document.getElementById('next-month');
const dateForm = document.getElementById('date-form');
const selectedDateInput = document.getElementById('selected-date');

const months = ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'];
let currentDate = new Date();

function populateMonthSelect() {
    months.forEach((month, index) => {
        const option = document.createElement('option');
        option.value = index;
        option.textContent = month;
        monthSelect.appendChild(option);
    });
}

function populateYearSelect() {
    const currentYear = new Date().getFullYear();
    for (let year = currentYear - 50; year <= currentYear + 50; year++) {
        const option = document.createElement('option');
        option.value = year;
        option.textContent = year;
        yearSelect.appendChild(option);
    }
}

function updateCalendar() {
    const month = currentDate.getMonth();
    const year = currentDate.getFullYear();
    
    monthSelect.value = month;
    yearSelect.value = year;

    numberDays.innerHTML = '';
    
    const firstDayOfMonth = new Date(year, month, 1).getDay();
    const lastDateOfMonth = new Date(year, month + 1, 0).getDate();
    const lastDayOfPrevMonth = new Date(year, month, 0).getDate();

    let days = [];

    const leadingDays = (firstDayOfMonth + 6) % 7;
    for (let i = leadingDays; i > 0; i--) {
        days.push(`<span class="mes-anterior">${lastDayOfPrevMonth - i + 1}</span>`);
    }

    for (let i = 1; i <= lastDateOfMonth; i++) {
        days.push(`<span class="dia-mes-atual">${i}</span>`);
    }

    const trailingDays = 7 - (days.length % 7);
    if (trailingDays < 7) {
        for (let i = 1; i <= trailingDays; i++) {
            days.push(`<span class="proximo-mes">${i}</span>`);
        }
    }

    numberDays.innerHTML = days.join('');

    document.querySelectorAll('.dia-mes-atual').forEach(day => {
        day.addEventListener('click', () => {
            const selectedDay = day.textContent;
            const selectedMonth = monthSelect.value;
            const selectedYear = yearSelect.value;
            const fullDate = new Date(selectedYear, selectedMonth, selectedDay);
            selectedDateInput.value = fullDate.toISOString().split('T')[0];
            dateForm.submit();
        });
    });
}

prevMonthButton.addEventListener('click', () => {
    currentDate.setMonth(currentDate.getMonth() - 1);
    updateCalendar();
});

nextMonthButton.addEventListener('click', () => {
    currentDate.setMonth(currentDate.getMonth() + 1);
    updateCalendar();
});

monthSelect.addEventListener('change', () => {
    currentDate.setMonth(monthSelect.value);
    updateCalendar();
});

yearSelect.addEventListener('change', () => {
    currentDate.setFullYear(yearSelect.value);
    updateCalendar();
});

populateMonthSelect();
populateYearSelect();
updateCalendar();
