import tkinter as tk
from tkinter import messagebox
import math

class Calculator(tk.Tk):
    def __init__(self):
        super().__init__()

        self.title("Scientific Calculator")
        self.geometry("500x700")  # Resize to make it larger and cleaner
        self.configure(bg='#2E2E2E')  # Dark background

        self.result_var = tk.StringVar()
        self.result_var.set("0")

        self.create_widgets()

    def create_widgets(self):
        # Display Area
        display_frame = tk.Frame(self, height=100, bg='#2E2E2E')
        display_frame.pack(fill='both')

        display = tk.Label(display_frame, textvariable=self.result_var, font=('Arial', 24), bg='#2E2E2E', fg='white', anchor='e')
        display.pack(fill='both', padx=20, pady=10)

        # Buttons
        button_frame = tk.Frame(self)
        button_frame.pack(padx=20, pady=20)

        buttons = [
            ('7', '8', '9', '/', 'sqrt', 'exp'),
            ('4', '5', '6', '*', 'pow', 'log'),
            ('1', '2', '3', '-', 'sin', 'cos'),
            ('0', '.', '=', '+', 'tan', 'pi')
        ]

        for row in buttons:
            button_row = tk.Frame(button_frame)
            button_row.pack(fill='x', pady=5)
            for button in row:
                button_widget = tk.Button(button_row, text=button, font=('Arial', 16), bg='#4E4E4E', fg='white', height=2, width=4,
                                          command=lambda b=button: self.on_button_click(b))
                button_widget.pack(side='left', expand=True, fill='both')

        # Clear Button
        clear_button = tk.Button(self, text="C", font=('Arial', 16), bg='#D32F2F', fg='white', height=2, width=4, command=self.clear_display)
        clear_button.pack(pady=10)

    def on_button_click(self, button):
        current_value = self.result_var.get()

        if button == "=":
            try:
                # Evaluate the expression safely, adding special handling for scientific operations
                expression = current_value.replace("sqrt", "**0.5").replace("pow", "**").replace("exp", "**").replace("log", "math.log10")
                expression = expression.replace("sin", "math.sin").replace("cos", "math.cos").replace("tan", "math.tan")
                expression = expression.replace("pi", "math.pi")
                result = eval(expression)
                self.result_var.set(str(result))
            except Exception as e:
                messagebox.showerror("Error", f"Invalid expression: {e}")
        elif button == "C":
            self.clear_display()
        else:
            if current_value == "0":
                self.result_var.set(button)
            else:
                self.result_var.set(current_value + button)

    def clear_display(self):
        self.result_var.set("0")  # Reset the display to zero

if __name__ == "__main__":
    app = Calculator()
    app.mainloop()
