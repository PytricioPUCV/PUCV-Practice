"""Conversor de HTML a PDF con interfaz gráfica.

Requisitos (una sola vez):
    py -m pip install playwright
    py -m playwright install chromium

Ejecución:
    py html_a_pdf.py

El programa abre un HTML local en Chromium y lo imprime a PDF, conservando
CSS, imágenes y fondos. Los recursos relativos deben estar junto al HTML o
referenciados con rutas correctas.
"""

from __future__ import annotations

import threading
from pathlib import Path
import tkinter as tk
from tkinter import filedialog, messagebox, ttk


FORMATOS_PDF = {
    "A4": "A4",
    "Carta": "Letter",
    "Oficio": "Legal",
}


class ConversorHtmlPdf:
    def __init__(self, root: tk.Tk) -> None:
        self.root = root
        self.root.title("Convertir HTML a PDF")
        self.root.geometry("640x300")
        self.root.minsize(640, 300)
        self.root.resizable(False, False)

        self.archivo_html = tk.StringVar()
        self.archivo_pdf = tk.StringVar()
        self.formato = tk.StringVar(value="A4")
        self.horizontal = tk.BooleanVar(value=False)
        self.estado = tk.StringVar(value="Selecciona un archivo HTML y el lugar donde guardar el PDF.")

        self._crear_interfaz()

    def _crear_interfaz(self) -> None:
        contenedor = ttk.Frame(self.root, padding=20)
        contenedor.pack(fill="both", expand=True)
        contenedor.columnconfigure(1, weight=1)

        ttk.Label(
            contenedor,
            text="Conversor HTML a PDF",
            font=("Segoe UI", 15, "bold"),
        ).grid(row=0, column=0, columnspan=3, sticky="w", pady=(0, 16))

        ttk.Label(contenedor, text="Archivo HTML:").grid(row=1, column=0, sticky="w", pady=6)
        ttk.Entry(contenedor, textvariable=self.archivo_html, state="readonly").grid(
            row=1, column=1, sticky="ew", padx=(10, 8), pady=6
        )
        ttk.Button(contenedor, text="Seleccionar...", command=self.seleccionar_html).grid(
            row=1, column=2, sticky="ew", pady=6
        )

        ttk.Label(contenedor, text="Guardar PDF en:").grid(row=2, column=0, sticky="w", pady=6)
        ttk.Entry(contenedor, textvariable=self.archivo_pdf, state="readonly").grid(
            row=2, column=1, sticky="ew", padx=(10, 8), pady=6
        )
        ttk.Button(contenedor, text="Elegir destino...", command=self.seleccionar_destino).grid(
            row=2, column=2, sticky="ew", pady=6
        )

        opciones = ttk.Frame(contenedor)
        opciones.grid(row=3, column=0, columnspan=3, sticky="w", pady=(10, 6))
        ttk.Label(opciones, text="Tamaño de página:").pack(side="left")
        ttk.Combobox(
            opciones,
            textvariable=self.formato,
            values=list(FORMATOS_PDF.keys()),
            state="readonly",
            width=10,
        ).pack(side="left", padx=(8, 22))
        ttk.Checkbutton(opciones, text="Orientación horizontal", variable=self.horizontal).pack(side="left")

        self.boton_convertir = ttk.Button(
            contenedor,
            text="Convertir a PDF",
            command=self.convertir,
        )
        self.boton_convertir.grid(row=4, column=0, columnspan=3, sticky="ew", pady=(16, 10))

        ttk.Separator(contenedor).grid(row=5, column=0, columnspan=3, sticky="ew", pady=(2, 10))
        ttk.Label(
            contenedor,
            textvariable=self.estado,
            justify="left",
            wraplength=590,
        ).grid(row=6, column=0, columnspan=3, sticky="w")

    def seleccionar_html(self) -> None:
        ruta = filedialog.askopenfilename(
            title="Selecciona un archivo HTML",
            filetypes=[
                ("Archivos HTML", "*.html *.htm"),
                ("Todos los archivos", "*.*"),
            ],
        )
        if not ruta:
            return

        html = Path(ruta)
        self.archivo_html.set(str(html))

        # Propone un nombre inicial; el usuario aún puede escoger libremente el destino.
        if not self.archivo_pdf.get():
            self.archivo_pdf.set(str(html.with_suffix(".pdf")))
        self.estado.set("Archivo HTML seleccionado. Ahora elige el destino o convierte directamente.")

    def seleccionar_destino(self) -> None:
        nombre_inicial = "documento.pdf"
        if self.archivo_html.get():
            nombre_inicial = Path(self.archivo_html.get()).with_suffix(".pdf").name

        ruta = filedialog.asksaveasfilename(
            title="Guardar PDF como",
            defaultextension=".pdf",
            initialfile=nombre_inicial,
            filetypes=[("Documento PDF", "*.pdf")],
        )
        if ruta:
            self.archivo_pdf.set(str(Path(ruta)))
            self.estado.set("Destino seleccionado. Pulsa “Convertir a PDF”.")

    def convertir(self) -> None:
        if not self.archivo_html.get():
            messagebox.showwarning("Falta el HTML", "Primero selecciona el archivo HTML que deseas convertir.")
            return
        if not self.archivo_pdf.get():
            messagebox.showwarning("Falta el destino", "Elige dónde guardar el PDF resultante.")
            return

        html = Path(self.archivo_html.get()).expanduser().resolve()
        pdf = Path(self.archivo_pdf.get()).expanduser().resolve()

        if not html.exists() or not html.is_file():
            messagebox.showerror("Archivo no encontrado", "El archivo HTML seleccionado ya no existe o no puede abrirse.")
            return
        if html.suffix.lower() not in {".html", ".htm"}:
            messagebox.showerror("Formato no válido", "El archivo de entrada debe terminar en .html o .htm.")
            return
        if html == pdf:
            messagebox.showerror("Destino no válido", "El PDF de salida debe ser diferente del archivo HTML de origen.")
            return

        self.boton_convertir.configure(state="disabled")
        self.estado.set("Convirtiendo. Esto puede tardar unos segundos según el contenido del HTML...")

        hilo = threading.Thread(
            target=self._convertir_en_segundo_plano,
            args=(html, pdf, FORMATOS_PDF[self.formato.get()], self.horizontal.get()),
            daemon=True,
        )
        hilo.start()

    def _convertir_en_segundo_plano(self, html: Path, pdf: Path, formato: str, horizontal: bool) -> None:
        try:
            self._renderizar_pdf(html, pdf, formato, horizontal)
        except Exception as error:  # Se muestra una explicación clara al usuario.
            detalle = self._mensaje_error(error)
            self.root.after(0, self._conversion_fallida, detalle)
        else:
            self.root.after(0, self._conversion_exitosa, pdf)

    @staticmethod
    def _renderizar_pdf(html: Path, pdf: Path, formato: str, horizontal: bool) -> None:
        try:
            from playwright.sync_api import sync_playwright
        except ModuleNotFoundError as error:
            raise RuntimeError(
                "No está instalada la librería Playwright.\n\n"
                "Abre una terminal en la carpeta del script y ejecuta:\n"
                "py -m pip install playwright\n"
                "py -m playwright install chromium"
            ) from error

        pdf.parent.mkdir(parents=True, exist_ok=True)

        with sync_playwright() as playwright:
            # El argumento permite que el HTML abra imágenes y CSS locales referidos por rutas relativas.
            navegador = playwright.chromium.launch(
                headless=True,
                args=["--allow-file-access-from-files"],
            )
            try:
                pagina = navegador.new_page(viewport={"width": 1440, "height": 1000})
                pagina.goto(html.as_uri(), wait_until="load", timeout=60_000)

                # Espera fuentes web/locales sin bloquear la conversión si alguna no responde.
                try:
                    pagina.wait_for_function(
                        "document.fonts ? document.fonts.status === 'loaded' : true",
                        timeout=10_000,
                    )
                except Exception:
                    pass

                pagina.emulate_media(media="print")
                pagina.pdf(
                    path=str(pdf),
                    format=formato,
                    landscape=horizontal,
                    print_background=True,
                    prefer_css_page_size=True,
                    display_header_footer=False,
                    margin={"top": "0", "right": "0", "bottom": "0", "left": "0"},
                )
            finally:
                navegador.close()

        if not pdf.exists() or pdf.stat().st_size == 0:
            raise RuntimeError("La conversión terminó, pero no se pudo crear un PDF válido.")

    @staticmethod
    def _mensaje_error(error: Exception) -> str:
        texto = str(error)
        minusculas = texto.lower()

        if "executable doesn't exist" in minusculas or "please run the following command to download new browsers" in minusculas:
            return (
                "Playwright está instalado, pero Chromium aún no está disponible.\n\n"
                "Abre una terminal en la carpeta del script y ejecuta:\n"
                "py -m playwright install chromium"
            )
        if "net::err_file_not_found" in minusculas:
            return "No fue posible abrir el HTML. Verifica que el archivo no haya sido movido o eliminado."

        return f"No se pudo convertir el archivo.\n\nDetalle técnico:\n{texto}"

    def _conversion_exitosa(self, pdf: Path) -> None:
        self.boton_convertir.configure(state="normal")
        self.estado.set(f"Conversión completada correctamente:\n{pdf}")
        messagebox.showinfo("PDF creado", f"El PDF se creó correctamente en:\n\n{pdf}")

    def _conversion_fallida(self, detalle: str) -> None:
        self.boton_convertir.configure(state="normal")
        self.estado.set("La conversión no se pudo completar.")
        messagebox.showerror("Error al convertir", detalle)


def main() -> None:
    root = tk.Tk()
    ConversorHtmlPdf(root)
    root.mainloop()


if __name__ == "__main__":
    main()
