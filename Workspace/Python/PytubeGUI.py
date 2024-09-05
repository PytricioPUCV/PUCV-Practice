import tkinter as tk
from tkinter import messagebox, filedialog
import yt_dlp
from tkinter import ttk
import threading

def descargar_contenido():
    url = url_entry.get().strip()
    if not url:
        messagebox.showerror("Error", "La URL no puede estar vacía.")
        return

    video_title = obtener_titulo_video(url)
    if not video_title:
        messagebox.showerror("Error", "No se pudo obtener el título del video.")
        return

    opcion = var_opcion.get()
    if opcion == 1:
        ydl_opts = {
            'format': 'best[ext=mp4]',
            'outtmpl': f'{video_title}.%(ext)s',
        }
    elif opcion == 2:
        ydl_opts = {
            'format': 'bestaudio/best',
            'outtmpl': f'{video_title}.%(ext)s',
        }
    else:
        messagebox.showerror("Error", "Opción no válida.")
        return

    # Deshabilitar los widgets durante la descarga
    url_entry.config(state=tk.DISABLED)
    descarga_button.config(state=tk.DISABLED)
    progress_label.config(text="Descargando...")

    # Ejecutar la descarga en un hilo separado
    threading.Thread(target=realizar_descarga, args=(url, ydl_opts)).start()

def realizar_descarga(url, ydl_opts):
    try:
        with yt_dlp.YoutubeDL(ydl_opts) as ydl:
            ydl.download([url])
        root.after(0, lambda: mostrar_mensaje("¡Descarga completa!", "success"))
    except Exception as e:
        root.after(0, lambda: mostrar_mensaje(f"Error durante la descarga: {e}", "error"))
    finally:
        # Habilitar los widgets después de la descarga
        root.after(0, lambda: (url_entry.config(state=tk.NORMAL), descarga_button.config(state=tk.NORMAL), progress_label.config(text="")))

def mostrar_mensaje(mensaje, tipo):
    if tipo == "success":
        messagebox.showinfo("Éxito", mensaje)
    elif tipo == "error":
        messagebox.showerror("Error", mensaje)

def obtener_titulo_video(url):
    """Obtiene el título del video sin descargarlo."""
    try:
        with yt_dlp.YoutubeDL() as ydl:
            info = ydl.extract_info(url, download=False)
            return info.get('title', 'Título no disponible')
    except Exception as e:
        return None

# Crear la ventana principal
root = tk.Tk()
root.title("Descargador de YouTube")

# Crear widgets usando grid para una mejor disposición
tk.Label(root, text="Ingrese la URL del video de YouTube:").grid(row=0, column=0, padx=10, pady=10, sticky="e")
url_entry = tk.Entry(root, width=50)
url_entry.grid(row=0, column=1, padx=10, pady=10)

tk.Label(root, text="Selecciona una opción:").grid(row=1, column=0, padx=10, pady=10, sticky="e")
var_opcion = tk.IntVar()
tk.Radiobutton(root, text="Descargar video en MP4", variable=var_opcion, value=1).grid(row=1, column=1, padx=10, pady=5, sticky="w")
tk.Radiobutton(root, text="Descargar solo audio en formato original", variable=var_opcion, value=2).grid(row=2, column=1, padx=10, pady=5, sticky="w")

descarga_button = tk.Button(root, text="Descargar", command=descargar_contenido)
descarga_button.grid(row=3, column=0, columnspan=2, padx=10, pady=20)

progress_label = tk.Label(root, text="")
progress_label.grid(row=4, column=0, columnspan=2, padx=10, pady=5)

# Ejecutar la aplicación
root.mainloop()
