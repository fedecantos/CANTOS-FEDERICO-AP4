import Funcionalidades.Usuario;
import Funcionalidades.Viajes;
import Funcionalidades.Vehiculos;
import Funcionalidades.Gastos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    // Método para imprimir el menú con estilo y emoticonos
    private static void Opciones() {
        System.out.println("╠════════════════════════════════════════════════════╣");
        System.out.println("║  Sistema de Gestión de Viajes Corporativos Arcor🍫 ║");
        System.out.println("╠════════════════════════════════════════════════════╣");
        System.out.println("║ 1. 🚗  Crear viaje                                 ║");
        System.out.println("║ 2. 📖️ Leer viaje                                   ║");
        System.out.println("║ 3. 🚫️ Cerrar programa                              ║");
        System.out.println("║ 4. ✒️️ Solicitar edición de viaje                   ║");
        System.out.println("║ 5. ✒️️ Solicitar eliminación de viaje               ║");
        System.out.println("║ 6. 📖️ Mostrar solicitudes                          ║");
        System.out.println("╚════════════════════════════════════════════════════╝");
        System.out.print("Ingrese una opción: ");
    }

     
    
    // Método para manejar el inicio de sesión
    private static int login(Scanner scanner) {
        Usuario usuario = new Usuario();
        while (true) {
            System.out.print("Ingrese su email: ");
            String email = scanner.nextLine();
            System.out.print("Ingrese su contraseña: ");
            String contrasena = scanner.nextLine();

            // Verificar las credenciales del usuario
            if (usuario.verificarCredenciales(email, contrasena)) {

                System.out.println("Verificación correcta");
                return 1;

            } else {
                System.out.println("Email o contraseña incorrectos.");
                System.out.print("¿Desea crear una cuenta? (s/n): ");
                String respuesta = scanner.nextLine();
                if (respuesta.equalsIgnoreCase("s")) {
                    return crearCuenta(scanner);
                }
            }
        }
    }

    // Método para crear una nueva cuenta de usuario
    private static int crearCuenta(Scanner scanner) {
        System.out.print("Ingrese su nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese su apellido: ");
        String apellido = scanner.nextLine();
        System.out.print("Ingrese su email: ");
        String email = scanner.nextLine();
        System.out.print("Ingrese su contraseña: ");
        String contrasena = scanner.nextLine();

        Usuario nuevoUsuario = new Usuario(0, nombre, apellido, email, contrasena);
        if (nuevoUsuario.create(nuevoUsuario)) {

            System.out.println("Usuario creado correctamente");
            return 1;

        } else {
            System.out.println("Error al crear la cuenta. Inténtelo de nuevo.");
            return -1;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bienvenido al sistema de gestión de viajes corporativos.");
        int userId = login(scanner);
        if (userId == -1) {
            System.out.println("Error al iniciar sesión o crear cuenta.");
            return;
        }

        ArrayList<String> solicitudes = new ArrayList<>();

        
        boolean salir = false;
        while (!salir) {
            Opciones();

            int opcion = scanner.nextInt();
            scanner.nextLine();
            LocalDateTime now = LocalDateTime.now(); // Obtener la hora actual
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedNow = now.format(formatter);
            
            switch (opcion) {

                case 1:
                    // Crear viaje
                    System.out.print("✈️ Ingrese la fecha de salida (YYYY-MM-DD): ");
                    String fechaSalida = scanner.nextLine();
                    System.out.print("✈️ Ingrese la fecha de regreso (YYYY-MM-DD): ");
                    String fechaRegreso = scanner.nextLine();
                    System.out.print("✈️ Ingrese el destino: ");
                    String destino = scanner.nextLine();
                    System.out.print("✈️ Ingrese el propósito: ");
                    String proposito = scanner.nextLine();
                    System.out.print("✈️ Ingrese el estado: ");
                    String estadoViaje = scanner.nextLine();

                    Viajes nuevoViaje = new Viajes(0, userId, destino, fechaSalida, fechaRegreso, estadoViaje);
                    boolean viajeCreado = nuevoViaje.create(nuevoViaje);
                    if (viajeCreado) {
                        int viajeId = nuevoViaje.getViajeId();
                        System.out.println("Viaje creado exitosamente con ID: " + viajeId);

                        // Crear gastos asociados
                        System.out.print("💵 ¿Desea agregar gastos a este viaje? (s/n): ");
                        String agregarGastos = scanner.nextLine();
                        while (agregarGastos.equalsIgnoreCase("s")) {
                            System.out.print("💵 Ingrese el concepto del gasto: ");
                            String conceptoGasto = scanner.nextLine();
                            System.out.print("💵 Ingrese el monto del gasto: ");
                            double montoGasto = scanner.nextDouble();
                            scanner.nextLine();
                            System.out.print("💵 Ingrese la fecha del gasto (YYYY-MM-DD): ");
                            String fechaGasto = scanner.nextLine();
                            System.out.print("💵 ¿El gasto está aprobado? (true/false): ");
                            boolean aprobadoGasto = scanner.nextBoolean();
                            scanner.nextLine();

                            Gastos nuevoGasto = new Gastos(0, viajeId, conceptoGasto, montoGasto, fechaGasto, aprobadoGasto);
                            boolean gastoCreado = nuevoGasto.create(nuevoGasto);
                            if (gastoCreado) {
                                System.out.println("Gasto creado exitosamente.");
                            } else {
                                System.out.println("Error al crear el gasto.");
                            }

                            System.out.print("💵 ¿Desea agregar otro gasto? (s/n): ");
                            agregarGastos = scanner.nextLine();
                        }

                        // Crear vehículo asociado
                        System.out.print("🚗 ¿Desea agregar un vehículo a este viaje? (s/n): ");
                        String agregarVehiculo = scanner.nextLine();
                        if (agregarVehiculo.equalsIgnoreCase("s")) {
                            System.out.print("🚗 Ingrese el nombre del vehículo: ");
                            String nombreVehiculo = scanner.nextLine();

                            Vehiculos nuevoVehiculo = new Vehiculos(0, userId, nombreVehiculo);
                            boolean vehiculoCreado = nuevoVehiculo.create(nuevoVehiculo);
                            if (vehiculoCreado) {
                                System.out.println("Vehículo creado exitosamente.");
                            } else {
                                System.out.println("Error al crear el vehículo.");
                            }
                        }
                    } else {
                        System.out.println("Error al crear el viaje.");
                    }
                    break;
                case 2:
                    // Leer viajes
                    Viajes viajeParaLeer = new Viajes();
                    List<Viajes> listaViajes = viajeParaLeer.readAll();
                    if (listaViajes.isEmpty()) {
                        System.out.println("No hay viajes disponibles.");
                    } else {
                        for (Viajes v : listaViajes) {
                            System.out.println(v);
                        }
                    }
                    break;
                case 3:
                    salir = true;
                    System.out.println("Saliendo del programa...");
                    break;


                case 4:
                    
                    String editarViaje = "Se solicitó al administrador la edición del viaje a las: " + formattedNow;
                    solicitudes.add(editarViaje);
                    System.out.println(editarViaje);
                    break;
                case 5:
                    String borrarGasto = "Se solicitó al administrador el borrado del gasto a las: " + formattedNow;
                    solicitudes.add(borrarGasto);
                    System.out.println(borrarGasto);
                    break;

                case 6:
                    System.out.println("Solicitudes realizadas:");
                    for (String solicitud : solicitudes) {
                        System.out.println(solicitud);
                    }
                    break;
                    
                default:
                    System.out.println("Opción inválida. Por favor, seleccione una opción válida.");
                    break;

            }
            try {
                Thread.sleep(1000); // Pausa de 1 segundo
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        scanner.close();
    }

    }
