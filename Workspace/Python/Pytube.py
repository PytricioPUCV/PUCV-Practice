import yt_dlp

def main():
    print("-" * 36)
    print("Bienvenido al descargador de PyTube!")
    print("-" * 36)
    print("\n")
    
    # Solicitar la URL del video de YouTube al usuario
    url = input("Ingrese la URL del video de YouTube: ").strip()

    if not url:
        print("La URL no puede estar vacía.")
        return

    # Inicializar yt_dlp.YoutubeDL solo una vez
    ydl = yt_dlp.YoutubeDL()

    # Obtener la información del video
    video_title = obtener_titulo_video(ydl, url)
    if video_title:
        print("\n")
        print("-" * 36)
        print("Información del video obtenida con éxito.")
        print(f"Título del video: {video_title}")
        print("-" * 36)
    else:
        print("-" * 36)
        print("No se pudo obtener el título del video.")
        return

    # Solicitar la opción de descarga al usuario
    while True:
        print("\nOpciones de descarga:")
        print("1. Descargar video en MP4 (con video y audio)")
        print("2. Descargar solo audio en formato original")
        print("3. Salir")

        opcion = input("Ingrese el número de la opción deseada (1, 2 o 3): ").strip()

        if opcion == '1':
            ydl_opts = configurar_opciones_descarga(opcion)
            break
        elif opcion == '2':
            ydl_opts = configurar_opciones_descarga(opcion)
            break
        elif opcion == '3':
            print("Saliendo del programa.")
            return
        else:
            print("Opción no válida. Por favor, elija 1, 2 o 3.")

    # Descargar el contenido según la opción elegida
    descargar_contenido(ydl, url, ydl_opts)
    print("\n")
    print("-" * 36)
    print("¡Descarga completa!")
    print("-" * 36)

def obtener_titulo_video(ydl, url):
    try:
        info = ydl.extract_info(url, download=False)
        return info.get('title', 'Título no disponible')
    except Exception as e:
        print(f"Error al obtener el título del video: {e}")
        return None

def configurar_opciones_descarga(opcion):
    """Configura las opciones de descarga según la opción elegida."""
    if opcion == '1':
        return {
            'format': 'best[ext=mp4]',  # Descargar el mejor formato MP4 disponible
            'outtmpl': '%(title)s.%(ext)s',  # Nombre del archivo basado en el título del video
        }
    elif opcion == '2':
        return {
            'format': 'bestaudio/best',  # Descargar solo el mejor audio disponible
            'outtmpl': '%(title)s.%(ext)s',  # Nombre del archivo basado en el título del video
        }

def descargar_contenido(ydl, url, ydl_opts):
    """Descarga el contenido según las opciones especificadas."""
    try:
        with yt_dlp.YoutubeDL(ydl_opts) as ydl:
            ydl.download([url])
    except Exception as e:
        print(f"Error durante la descarga: {e}")

if __name__ == "__main__":
    main()