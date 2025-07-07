import tkinter as tk

def show_check_state():
    if check_var.get () == 1:
        print ("Kotak centang dicentang.")
    else:
        print ("Kotak centang tidak dicentang.")

root = tk.Tk ()
root.title("Kotak Centang")
check_var = tk.IntVar ()
checkbox = tk.Checkbutton (root, text="Aktifkan Fitur", variable=check_var, command=show_check_state)
checkbox.pack(pady=10)
root.mainloop ()
    
