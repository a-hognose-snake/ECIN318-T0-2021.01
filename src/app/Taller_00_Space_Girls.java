package app;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Taller 0: Create a sales system for Compulipehipermegacomic
 * @author a_hognose_snake and dorime_a1
 * Grupo: SPACE GIRLS
 */
public class Taller_00_Space_Girls {

	public static void main(String[] args) throws IOException{

		//CANTIDAD DE PERSONAS
		int N = 100;
		
		//CANTIDAD DE PRODUCTOS
		int P = 100;
		
		//CANTIDAD DE TIENDAS
		int T = 100;
		
		//CANTIDAD DE COMPRAS POR PANTALLA
		int [] compra = new int [5];
		
		//PERSONAS
		String [] nombrePersona = new String [N];
		String [] apellidoPersona = new String [N];
		String [] rutPersona = new String [N];
		String [] clavePersona = new String [N];
		int [] saldoPersona = new int [N];
		int [][] inventarioPersona = new int [N][P];
		
		//SUMA MONTO GASTADO POR LA PERSONA
		int [] montoCompraPersona = new int [N];
		
		//SUMA PRODUCTOS DE LA PERSONA
		//max 20 productos
		int [] totalInvPersona = new int [N];
		
		//PRODUCTOS
		String [] producto = new String [P];
		// 0 = Comic | 1 = Manga
		String [] tipoProducto = new String[P];
		int [][] stockProducto = new int [T][P];
		int [][] precioStockProducto = new int [T][P];
		
		//TIENDAS
		String [] tienda = new String [T];
		//SUMA HACIA EL LADO MONTO REC (STOCK TIENDA[T][P] * PRECIO TIENDA[T][P])
		int [] montoRecTienda = new int [T];

		//LEER Y RELLENAR
		int cantTiendas = lecturaRellenadoTiendas(tienda, montoRecTienda);
		int cantProductos = lecturaRellenadoProductos(tipoProducto, producto, stockProducto, precioStockProducto, tienda, cantTiendas);
		int cantPersonas = lecturaRellenadoPersonas(nombrePersona, apellidoPersona, rutPersona, clavePersona, saldoPersona, inventarioPersona, cantProductos, producto);
		
		//CREAR SCANNER
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		
		while (true) {

			//INICIAR SESIÓN
			System.out.println("\n**********************************************************");
			System.out.println("INICIAR SESIÓN");
			System.out.println("**********************************************************");
			System.out.println("\nRUT:");
			String rutInput = scan.nextLine();
			rutInput = cambiaFormato(rutInput);
			Boolean existeUsuario = buscarRut(rutInput, rutPersona, cantPersonas);

			//USUARIO NO EXISTE
			while (existeUsuario == false) {
//				desplegarMenuErrorUsuario();	
				System.out.println("\nOPCIÓN: ");
				int op = Integer.parseInt(scan.nextLine());
				//INTENTAR NUEVAMENTE
				if (op == 1) {
					System.out.println("\n**********************************************************");
					System.out.println("INTENTA INICIAR SESIÓN NUEVAMENTE");
					System.out.println("**********************************************************");
					System.out.println("\nRUT:");
					rutInput = scan.nextLine();
					rutInput = cambiaFormato(rutInput);
					existeUsuario = buscarRut(rutInput, rutPersona, cantPersonas);
				}
				else {
				//REGISTRAR NUEVO USUARIO
					if (op == 2) {
						System.out.println("\n**********************************************************");
						System.out.println("REGISTRAR NUEVO USUARIO (RUT: "+rutInput+")");
						System.out.println("**********************************************************");
						System.out.println("\nNOMBRE: ");
						String nombreInput = scan.nextLine();
						System.out.println("\nAPELLIDO:");
						String apellidoInput = scan.nextLine();
						System.out.println("\nCONTRASEÑA:");
						String claveInput = scan.nextLine();
						registrarNuevoUsuario(rutInput, rutPersona, nombreInput, nombrePersona, apellidoInput, apellidoPersona, claveInput, clavePersona, cantPersonas);
						cantPersonas ++;
						
						System.out.println("\n**********************************************************");
						System.out.println("INICIAR SESIÓN");
						System.out.println("**********************************************************");
						System.out.println("\nRUT:");
						rutInput = scan.nextLine();
						rutInput = cambiaFormato(rutInput);
						existeUsuario = buscarRut(rutInput, rutPersona, cantPersonas);
					} 
					else {
				//CERRAR SISTEMA
						if (op ==3) {
							cerrarSistema(cantPersonas, producto, cantTiendas, cantProductos ,nombrePersona, apellidoPersona, rutPersona, clavePersona,saldoPersona,inventarioPersona, totalInvPersona, tipoProducto, precioStockProducto, stockProducto, tienda, montoRecTienda);
						} 
				//OPCIÓN INVÁLIDA
						else {
							System.out.println("Opción invalida.");
							continue;
						}
					}
				}
			}
			
			System.out.println("\nCONTRASEÑA:");
			String claveInput = scan.nextLine();
			Boolean accesoCorrecto = LogIn(rutInput, rutPersona, claveInput, clavePersona, cantPersonas);
			
			//CLAVE INCORRECTA
			while (accesoCorrecto == false) {
				desplegarMenuErrorClave();
				System.out.println("\nOPCIÓN: ");
				int op = Integer.parseInt(scan.nextLine());
				//INTENTAR NUEVAMENTE
				if (op == 1) {
					System.out.println("\n**********************************************************");
					System.out.println("INTENTA CONTRASEÑA NUEVAMENTE");
					System.out.println("**********************************************************");
					System.out.println("\nCONTRASEÑA:");
					claveInput = scan.nextLine();
					accesoCorrecto = LogIn(rutInput, rutPersona, claveInput, clavePersona, cantPersonas);
				} 
				else {
				//CERRAR SISTEMA
					if (op == 2) {
						cerrarSistema(cantPersonas, producto, cantTiendas, cantProductos ,nombrePersona, apellidoPersona, rutPersona, clavePersona,saldoPersona,inventarioPersona, totalInvPersona, tipoProducto, precioStockProducto, stockProducto, tienda, montoRecTienda);	
				} 
				//OPCIÓN INVÁLIDA
					else {
						System.out.println("Opción invalida.");
						continue;
				}
				}
			}
			
			while (accesoCorrecto) {
				//ADMIN
				if (rutInput.equals("ADMIN") && claveInput.equals("ADMIN")) {
						desplegarMenuAdmin();
						System.out.println("\nOPCIÓN: ");
						int op = Integer.parseInt(scan.nextLine());
						//AGREGAR STOCK
						if (op == 1) {
							adminAgregarStock(cantTiendas, cantProductos, tienda, stockProducto, precioStockProducto, producto);	
						} 
						else {
						//INFORMACIÓN DE RECAUDACIÓN
							if (op == 2) {
								desplegarRecaudacion(tienda, montoRecTienda, cantTiendas);
						} 
							else {
						//INFORMACIÓN DE COMPRADORES
								if (op == 3) {
									desplegarInfoCompradores(nombrePersona, apellidoPersona, rutPersona, inventarioPersona, totalInvPersona, cantProductos, cantPersonas, producto);
						} 
								else {
						//COMIC VS MANGA
									if (op == 4) {
										desplegarComicVsManga(compra);				
						} 
									else {
						//INICIAR OTRA SESIÓN 
										if (op == 5) {
											break;	
						} 
										else {
						//CERRAR SISTEMA
											if (op == 6) {
												cerrarSistema(cantPersonas, producto, cantTiendas, cantProductos ,nombrePersona, apellidoPersona, rutPersona, clavePersona,saldoPersona,inventarioPersona, totalInvPersona, tipoProducto, precioStockProducto, stockProducto, tienda, montoRecTienda);	
											} 
						//OPCIÓN INVÁLIDA
											else {
												System.out.println("/nOpción invalida.");
												continue;
											}		
						}
						}
						}
						}
						}
					}//fin while (reingreso de opcion
				//USUARIO NORMAL
				else {
					desplegarMenuUsuario();	
					System.out.println("\nOPCIÓN: ");
					int op = Integer.parseInt(scan.nextLine());
					//INFORMACIÓN DEL USUARIO
					if (op == 1) {
						desplegarInfoUsuario(rutInput, rutPersona, nombrePersona, apellidoPersona, saldoPersona, inventarioPersona, cantPersonas, cantProductos, producto);
					} 
					else {
					//AÑADIR SALDO
						if (op == 2) {
							agregarSaldo(rutInput, rutPersona, saldoPersona, cantPersonas);	
					} 
						else {
					//COMPRAR PRODUCTO
							if (op == 3) {
								desplegarComprarProducto(rutInput, rutPersona, saldoPersona, cantPersonas, producto, stockProducto, precioStockProducto, cantProductos, tienda, cantTiendas, montoCompraPersona, totalInvPersona, montoRecTienda, tipoProducto, inventarioPersona, compra);
						} 
							else {
					//INICIAR OTRA SESIÓN 
								if (op == 4) {
									break;
							} 
								else {
					//CERRAR SISTEMA
									if (op == 5) {
										cerrarSistema(cantPersonas, producto, cantTiendas, cantProductos ,nombrePersona, apellidoPersona, rutPersona, clavePersona,saldoPersona,inventarioPersona, totalInvPersona, tipoProducto, precioStockProducto, stockProducto, tienda, montoRecTienda);	
									} 
					//OPCIÓN INVÁLIDA
									else {
										System.out.println("\nOpción invalida.");
										continue;
									}
							}
						}
					}
					}
				}
			}
		}
	}

	/**
	 * Function that takes input to buy a specific product
	 * @param rutInput
	 * @param rutPersona
	 * @param saldoPersona
	 * @param cantPersonas
	 * @param producto
	 * @param stockProducto
	 * @param precioStockProducto
	 * @param cantProductos
	 * @param tienda
	 * @param cantTiendas
	 * @param montoCompraPersona
	 * @param totalInvPersona
	 * @param montoRecTienda
	 * @param tipoProducto
	 * @param inventarioPersona
	 * @param compra
	 */
	private static void desplegarComprarProducto(String rutInput, String[] rutPersona, int[] saldoPersona, int cantPersonas,
	String[] producto, int[][] stockProducto, int[][] precioStockProducto, int cantProductos, String[] tienda,
	int cantTiendas, int[] montoCompraPersona, int[] totalInvPersona, int[] montoRecTienda,
	String[] tipoProducto, int[][] inventarioPersona, int[] compra) {
		//CREAR SCANNER
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		int userIndex = Index(rutPersona, rutInput, cantPersonas);
		System.out.println("\n**********************************************************");
		System.out.println("COMPRAR PRODUCTO");
		System.out.println("**********************************************************\n");
		System.out.println("SALDO: "+saldoPersona[userIndex]+" CLP\n");
		deplegarStringVector(tienda,cantTiendas);
		System.out.println("\nTIENDA EN LA QUE QUIERE COMPRAR:");
		int tiendaIndexInput = Integer.parseInt(scan.nextLine());
		System.out.println("\nPRODUCTOS EN "+tienda[tiendaIndexInput].toUpperCase());
		desplegarSPV(tiendaIndexInput, producto, stockProducto, cantProductos, precioStockProducto);
		System.out.println("\nPRODUCTO QUE QUIERE COMPRAR: ");
		int productoIndexInput = Integer.parseInt(scan.nextLine());
		comprarProducto(tiendaIndexInput, productoIndexInput, rutInput, rutPersona, tipoProducto, inventarioPersona, stockProducto, precioStockProducto, montoRecTienda, montoCompraPersona, totalInvPersona, saldoPersona, cantPersonas, compra);	
	}

	/**
	 * Fuction that allows user to buy a products and updates data related to buyer information
	 * @param tiendaIndexInput
	 * @param productoIndexInput
	 * @param rutInput
	 * @param rutPersona
	 * @param tipoProducto
	 * @param inventarioPersona
	 * @param stockProducto
	 * @param precioStockProducto
	 * @param montoRecTienda
	 * @param montoCompraPersona
	 * @param totalInvPersona
	 * @param saldoPersona
	 * @param cantPersonas
	 * @param compra
	 */
	private static void comprarProducto(int tiendaIndexInput, int productoIndexInput, String rutInput,
	String[] rutPersona, String[] tipoProducto, int[][] inventarioPersona, int[][] stockProducto,
	int[][] precioStockProducto, int[] montoRecTienda, int[] montoCompraPersona, int[] totalInvPersona,
	int[] saldoPersona, int cantPersonas, int [] compra) {
		int indexUsuario = Index(rutPersona, rutInput, cantPersonas);
		int precio = precioStockProducto[tiendaIndexInput][productoIndexInput];
		if (precio <= saldoPersona[indexUsuario]) {
			compra[0]++;
			System.out.println("\nHay saldo suficiente.\n");
			if (tipoProducto[productoIndexInput].equals("Manga")) {
				compra[1]++;
				compra[2] += precio;
				System.out.println("Usuario compro 1 Manga.");
			}
			else {
				compra[3]++;
				compra[4] += precio;
				System.out.println("Usuario compro 1 Anime.");
			}
			
			inventarioPersona[indexUsuario][productoIndexInput] += 1;
			stockProducto[tiendaIndexInput][productoIndexInput] -= 1;
			saldoPersona[indexUsuario] -= precio;
			montoRecTienda[tiendaIndexInput] += precio;
			montoCompraPersona[indexUsuario] += precio;
			totalInvPersona[indexUsuario] += 1;
			System.out.println("Operación exitosa.");
		} 
		else {
			System.out.println("\nNo hay saldo suficiente.");
		}
		
	}

	/**
	 * Function that displays product index, product name, price in store and stock in store
	 * @param tiendaIndexInput
	 * @param producto
	 * @param stockProducto
	 * @param cantProductos
	 * @param precioStockProducto
	 */
	private static void desplegarSPV(int tiendaIndexInput, 
	String[] producto, int[][] stockProducto, int cantProductos,
	int[][] precioStockProducto) {
		for (int i = 0; i < cantProductos; i++) {
			if ((producto[i] != null) && (stockProducto[tiendaIndexInput][i] > 0)) {
				System.out.println("["+i+"] "+producto[i].toUpperCase()+" | PRECIO EN TIENDA: "+precioStockProducto[tiendaIndexInput][i]+" CLP | STOCK: "+stockProducto[tiendaIndexInput][i]);
			}
		}
		
	}

	/**
	 * Function that allows you to add money to the account of a registered user
	 * @param rutInput
	 * @param rutPersona
	 * @param saldoPersona
	 * @param cantPersonas
	 */
	private static void agregarSaldo(String rutInput, String[] rutPersona, 
	int[] saldoPersona, int cantPersonas) {
		int userIndex = Index(rutPersona, rutInput, cantPersonas);
		//CREAR SCANNER
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		System.out.println("\n**********************************************************");
		System.out.println("AÑADIR SALDO");
		System.out.println("**********************************************************\n");
		System.out.println("SALDO: "+saldoPersona[userIndex]+" CLP");
		System.out.println("CANTIDAD A AÑADIR EN CLP: ");
		int saldoInput = Integer.parseInt(scan.nextLine());
		saldoPersona[userIndex] += saldoInput;
		System.out.println("\nSALDO ACTUALIZADO: "+saldoPersona[userIndex]+" CLP");
		System.out.println("Operación exitosa.");
	}

	/**
	 * Function that displays the information of registered users
	 * @param rutInput
	 * @param rutPersona
	 * @param nombrePersona
	 * @param apellidoPersona
	 * @param saldoPersona
	 * @param inventarioPersona
	 * @param cantPersonas
	 * @param cantProductos
	 * @param producto
	 */
	private static void desplegarInfoUsuario(String rutInput, String[] rutPersona, 
	String[] nombrePersona, String[] apellidoPersona, int[] saldoPersona, int[][] inventarioPersona, int cantPersonas,
	int cantProductos, String[] producto) {
		System.out.println("\n**********************************************************");
		System.out.println("INFORMACIÓN DEL USUARIO");
		System.out.println("**********************************************************\n");
		int userIndex = Index(rutPersona, rutInput, cantPersonas);
		System.out.println("USUARIO: "+nombrePersona[userIndex].toUpperCase()+" "+apellidoPersona[userIndex].toUpperCase()+"\n");
		System.out.println("INVENTARIO DE "+nombrePersona[userIndex].toUpperCase()+" "+apellidoPersona[userIndex].toUpperCase()+" (MAX = 20 PRODUCTOS)");
		despliegaInventarioConNombre(userIndex, inventarioPersona, producto, cantProductos);
		System.out.println("\nSALDO: "+saldoPersona[userIndex]+" CLP");	
		}

	@SuppressWarnings("unused")
	private static void desplegarComicVsManga(int [] compra) {
		System.out.println("\n**********************************************************");
		System.out.println("COMIC VS MANGA");
		System.out.println("**********************************************************\n");
		if (compra[0] == 0) {
			System.out.println("NO SE HAN REALIZADO VENTAS NUEVAS.");
			
		} else {
			System.out.println("COMICS");
			System.out.println("TOTAL VENDIDOS: "+compra[1]);
			System.out.println("TOTAL RECAUDADO: "+compra[2]+" CLP");
			System.out.println("\nMANGAS");
			System.out.println("TOTAL VENDIDOS: "+compra[3]);
			System.out.println("TOTAL RECAUDADO: "+compra[4]+" CLP");
		}
		
	}

	/**
	 * Function that displays the buyer information of registered users
	 * @param nombrePersona
	 * @param apellidoPersona
	 * @param rutPersona
	 * @param inventarioPersona
	 * @param totalInvPersona
	 * @param cantProductos
	 * @param cantPersonas
	 * @param producto
	 */
	private static void desplegarInfoCompradores(String[] nombrePersona, String[] apellidoPersona, 
	String[] rutPersona, int[][] inventarioPersona, int[] totalInvPersona, int cantProductos, 
	int cantPersonas, String [] producto) {
		int max = -100;
		int maxIndex = 0;
		
		int min = 100;
		int minIndex = 0;
		
		//RECORRE COLUMNA
		for (int i = 0; i < cantProductos; i++) {
		//RECORRE FILA
			for (int j = 0; j < cantPersonas; j++) {
		//SUMA FILA
				totalInvPersona[j] += inventarioPersona[j][i];
			}
		}
		for (int i = 0; i < cantPersonas; i++) {
			if (totalInvPersona[i] < min) {
				min = totalInvPersona[i];	
				minIndex = i;
			}
			if (totalInvPersona[i] > max) {
				max = totalInvPersona[i];
				maxIndex = i;
				
			}
		}
		System.out.println("\n**********************************************************");
		System.out.println("INFORMACIÓN DE COMPRADORES");
		System.out.println("**********************************************************\n");
		System.out.println("PERSONA QUE ADQUIRIÓ MÁS PRODUCTOS:");
		System.out.println(nombrePersona[maxIndex].toUpperCase()+" "+apellidoPersona[maxIndex].toUpperCase());
		System.out.println("INVENTARIO DE "+nombrePersona[maxIndex].toUpperCase()+" "+apellidoPersona[maxIndex].toUpperCase());
		despliegaInventarioConNombre(maxIndex,inventarioPersona,producto,cantProductos);
		System.out.println("TOTAL: "+max);
		System.out.println("\nPERSONA QUE ADQUIRIÓ MENOS PRODUCTOS:");
		System.out.println(nombrePersona[minIndex].toUpperCase()+" "+apellidoPersona[minIndex].toUpperCase());
		System.out.println("INVENTARIO DE "+nombrePersona[minIndex].toUpperCase()+" "+apellidoPersona[minIndex].toUpperCase());
		despliegaInventarioConNombre(minIndex,inventarioPersona,producto,cantProductos);
		System.out.println("TOTAL: "+min);
		}

	/**
	 * Function that displays quantity of products in inventory and its name
	 * @param indexFila
	 * @param inventario
	 * @param columna
	 * @param cantcolumnas
	 */
	private static void despliegaInventarioConNombre(int indexFila, 
	int[][] inventario, String[] columna, int cantcolumnas) {
		Boolean sinProducto =true;
		for (int i = 0; i < cantcolumnas; i++) {
			if (inventario[indexFila][i] > 0) {
				System.out.println(inventario[indexFila][i]+" "+columna[i]);
				sinProducto = false;
			}
		}
		if (sinProducto) {
			System.out.println("0 PRODUCTOS");
			
		} 
		
	}

	/**
	 * Function that displays earnings per store and the total earning of the company that owns all the stores
	 * @param tienda
	 * @param montoRecTienda
	 * @param cantTiendas
	 */
	@SuppressWarnings("unused")
	private static void desplegarRecaudacion(String[] tienda, int[] montoRecTienda, int cantTiendas) {
		int suma = 0;
		System.out.println("\n**********************************************************");
		System.out.println("RECAUDACIÓN POR TIENDA");
		System.out.println("**********************************************************\n");
		for (int i = 0; i < cantTiendas; i++) {
			System.out.println(tienda[i].toUpperCase()+": "+montoRecTienda[i]+" CLP");
			suma += montoRecTienda[i];
		}
		System.out.println("\nRECAUDACIÓN TOTAL DE LA EMPRESA: "+suma+" CLP");
	}

	/**
	 * Function that allows admin to add stock to a store
	 * @param cantTiendas
	 * @param cantProductos
	 * @param tienda
	 * @param stockProducto
	 * @param precioStockProducto
	 * @param producto
	 */
	@SuppressWarnings("unused")
	private static void adminAgregarStock(int cantTiendas, 
	int cantProductos, String[] tienda, int[][] stockProducto, 
	int[][] precioStockProducto, String[] producto) {
		//CREAR SCANNER
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		System.out.println("\n**********************************************************");
		System.out.println("AGREGAR STOCK");
		System.out.println("**********************************************************\n");
		deplegarStringVector(tienda,cantTiendas);
		System.out.println("\nTIENDA A LA QUE LE QUIERE AGREGAR STOCK:");
		int tiendaIndexInput = Integer.parseInt(scanner.nextLine());
		System.out.println("\nPRODUCTOS DISPONIBLES EN "+tienda[tiendaIndexInput].toUpperCase()+":");
		desplegarSyP(tiendaIndexInput, producto, stockProducto, cantProductos, precioStockProducto);
		System.out.println("\nPRODUCTO AL QUE LE QUIERE AGREGAR STOCK:");
		int productoIndexInput = Integer.parseInt(scanner.nextLine());
		System.out.println("\nSTOCK QUE LE QUIERE AGREGAR A "+producto[productoIndexInput].toUpperCase()+" EN "+tienda[tiendaIndexInput].toUpperCase()+":");
		int agregarStockInput = Integer.parseInt(scanner.nextLine());
		agregarMatrix(stockProducto, agregarStockInput, tiendaIndexInput, productoIndexInput);
	}

	/**
	 * Function that allows you to sum a quantiy to a especific section of a matrix
	 * @param matrix
	 * @param cantidad
	 * @param fila
	 * @param col
	 */
	private static void agregarMatrix(int[][] matrix, int cantidad, int fila, int col) {
		matrix[fila][col] += cantidad;	
		System.out.println("\nOperación exitosa.");
	}

	/**
	 * Function that displays product index, product name and its stock in store
	 * @param tiendaIndexInput
	 * @param producto
	 * @param stockProducto
	 * @param cantProductos
	 * @param precioStockProducto
	 */
	private static void desplegarSyP(int tiendaIndexInput, String[] producto, 
	int[][] stockProducto, int cantProductos, int [][] precioStockProducto) {
		for (int i = 0; i < cantProductos; i++) {
			if ((producto[i] != null) && (precioStockProducto[tiendaIndexInput][i] > 0)) {
				System.out.println("["+i+"] "+producto[i].toUpperCase()+" | STOCK: "+stockProducto[tiendaIndexInput][i]);	
			}
		}
	}

	/**
	 * Fuction that displays string vector with its index
	 * @param listaStrings
	 * @param cantidad
	 */
	private static void deplegarStringVector(String[] listaStrings, int cantidad) {
		for(int i=0 ; i < cantidad ; i++){
			if (listaStrings[i] != null) {
				System.out.println("["+i+"] "+listaStrings[i].toUpperCase());
		   } 
		}
	}

	/**
	 * Function that returns true if user is registered and the password is correct
	 * @param rutInput
	 * @param rutPersona
	 * @param claveInput
	 * @param clavePersona
	 * @param cantPersonas
	 * @return boolean
	 */
	private static Boolean LogIn(String rutInput, String[] rutPersona, 
	String claveInput, String[] clavePersona,
	int cantPersonas) {
		int index_rut = Index(rutPersona, rutInput, cantPersonas);
		//ES ADMIN
		if (rutInput.equals("ADMIN")) {
			if (claveInput.equals("ADMIN")) {
				return true;	
			} else {
				return false;
			}
		} 
		else {
		//LOS DATOS COINCIDEN
			if (claveInput.equals(clavePersona[index_rut])) {
				return true;
				
			} 
		//LOS DATOS NO COINCIDEN
			else {
				return false;
			}
		}
	}
	

	/**
	 * Function that displays user menu
	 */
	private static void desplegarMenuUsuario() {
		System.out.println("\n**********************************************************");
		System.out.println("MENÚ USUARIO");
		System.out.println("**********************************************************\n");
		System.out.println("[1] INFORMACIÓN DEL USUARIO");
		System.out.println("[2] AÑADIR SALDO");
		System.out.println("[3] COMPRAR PRODUCTO");
		System.out.println("[4] INICIAR OTRA SESIÓN");
		System.out.println("[5] CERRAR SISTEMA");	
	}

	/**
	 * Function that displays admin menu
	 */
	private static void desplegarMenuAdmin() {
		System.out.println("\n**********************************************************");
		System.out.println("MENÚ ADMIN");
		System.out.println("**********************************************************\n");
		System.out.println("[1] AGREGAR STOCK");
		System.out.println("[2] INFORMACIÓN DE RECAUDACIÓN");
		System.out.println("[3] INFORMACIÓN DE COMPRADORES");
		System.out.println("[4] COMIC VS MANGA");
		System.out.println("[5] INICIAR OTRA SESIÓN");	
		System.out.println("[6] CERRAR SISTEMA");
	}

	/**
	 * Function that displays menu for user that is not registered
	 */
	@SuppressWarnings("unused")
	private static void desplegarMenuErrorUsuario() {
		System.out.println("\n**********************************************************");
		System.out.println("ERROR: USUARIO NO REGISTRADO");
		System.out.println("**********************************************************\n");
		System.out.println("[1] INTENTAR INICIAR SESIÓN NUEVAMENTE");
		System.out.println("[2] REGISTRAR NUEVO USUARIO");
		System.out.println("[3] CERRAR SISTEMA");
	}

	/**
	 * Function that displays menu for user that puts the wrong password
	 */
	private static void desplegarMenuErrorClave() {
		System.out.println("\n**********************************************************");
		System.out.println("ERROR: CONTRASEÑA INCORRECTA");
		System.out.println("**********************************************************\n");
		System.out.println("[1] INTENTAR CONTRASEÑA NUEVAMENTE");
		System.out.println("[2] CERRAR SISTEMA");
	}


	/**
	 * Fuctions that updates everything, re-writes .txt files and closes program
	 * @param cantPersonas
	 * @param producto
	 * @param cantTiendas
	 * @param cantProductos
	 * @param nombrePersona
	 * @param apellidoPersona
	 * @param rutPersona
	 * @param clavePersona
	 * @param saldoPersona
	 * @param inventarioPersona
	 * @param totalInventarioPersona
	 * @param tipoProducto
	 * @param precioStockProducto
	 * @param stockProducto
	 * @param tienda
	 * @param montoRecTienda
	 * @throws IOException
	 */
	private static void cerrarSistema(int cantPersonas, String [] producto, int cantTiendas, int cantProductos ,String [] nombrePersona, String [] apellidoPersona, String [] rutPersona, String [] clavePersona, int [] saldoPersona, int [][] inventarioPersona, int[] totalInventarioPersona, String [] tipoProducto, int[][]precioStockProducto, int[][] stockProducto, String[] tienda, int [] montoRecTienda) throws IOException {
		System.out.println("\n**********************************************************");
		System.out.println("SESIÓN CERRADA");
		System.out.println("**********************************************************\n");
		actualizarPersonas(cantPersonas, cantProductos, nombrePersona, apellidoPersona, rutPersona, clavePersona, saldoPersona, inventarioPersona, totalInventarioPersona, producto);
		actualizarProductos(cantProductos, cantTiendas, cantPersonas, producto, tipoProducto,precioStockProducto,stockProducto,tienda);
		actualizarTiendas(cantTiendas,tienda,montoRecTienda);
		System.exit(0);
	}
	
	/**
	 * Fuction that updates data related to products and re-writes .txt
	 * @param cantProductos
	 * @param cantTiendas
	 * @param cantPersonas
	 * @param producto
	 * @param tipoProducto
	 * @param precioStockProducto
	 * @param stockProducto
	 * @param tienda
	 * @throws IOException
	 */
	private static void actualizarProductos(int cantProductos, int cantTiendas, int cantPersonas, String[] producto,
	String[] tipoProducto, int[][] precioStockProducto, int[][] stockProducto, String[] tienda) throws IOException {
		FileWriter file = new FileWriter("productos.txt");
        PrintWriter escritura = new PrintWriter(file);
        for (int i = 0; i < cantProductos; i++) {
        	for (int j = 0; j < cantTiendas; j++) {
        		if (stockProducto[j][i] > 0) {
        			escritura.println(tipoProducto[i]+","+producto[i]+","+stockProducto[j][i]+","+precioStockProducto[j][i]+","+tienda[j]);
				}
			}
		}
        file.close();
        escritura.close();
	}
	
	/**
	 * Function that updates data related to the stores and re-writes .txt
	 * @param cantTiendas
	 * @param tienda
	 * @param montoRecTienda
	 * @throws IOException
	 */
	private static void actualizarTiendas(int cantTiendas, String[] tienda, int[] montoRecTienda) throws IOException {
		FileWriter file = new FileWriter("tiendas.txt");
        PrintWriter escritura = new PrintWriter(file);
        for (int i = 0; i < cantTiendas ; i++){
            escritura.println(tienda[i]+","+montoRecTienda[i]);
        }
        file.close();
        escritura.close();
    }


	/**
	 * Function that updates data related to registered users and re-writes .txt
	 * @param cantPersonas
	 * @param cantProductos
	 * @param nombrePersona
	 * @param apellidoPersona
	 * @param rutPersona
	 * @param clavePersona
	 * @param saldoPersona
	 * @param inventarioPersona
	 * @param totalInventarioPersona
	 * @param producto
	 * @throws IOException
	 */
	private static void actualizarPersonas(int cantPersonas, int cantProductos, String[] nombrePersona, String[] apellidoPersona,
	String[] rutPersona, String[] clavePersona, int[] saldoPersona, int[][] inventarioPersona, int[] totalInventarioPersona, 
	String[] producto) throws IOException {
		FileWriter file = new FileWriter("personas.txt");
        PrintWriter escritura = new PrintWriter(file);
        int[] totalProductosInv = new int [cantPersonas];
        for (int i = 0; i < cantPersonas; i++) {
        	for (int j = 0; j < cantProductos; j++) {
        		if (inventarioPersona[i][j] > 0) {
        			totalProductosInv[i] += inventarioPersona[i][j];
				}
				
			}
			
		}
        for (int i = 0; i < cantPersonas; i++) {
        	int contador = 0;
        	escritura.print(nombrePersona[i]+ ","+apellidoPersona[i]+ ","+rutPersona[i]+ ","+clavePersona[i]+","+saldoPersona[i]);
        	for (int j = 0; j < cantProductos; j++) {
        		if (inventarioPersona[i][j] > 0) {
        			if (contador < totalProductosInv[i]) {
        				for (int j2 = 0; j2 < inventarioPersona[i][j]; j2++) {
        					escritura.print(","+producto[j]);
        					contador ++;
							
						}
        				
					}
        			
        	
        		}
			}
        	System.out.println();
        	escritura.print("\n");
		}
        file.close();
        escritura.close();
	}

	/**
	 * Function that takes input as parameter and registers new user
	 * @param rutInput
	 * @param rutPersona
	 * @param nombreInput
	 * @param nombrePersona
	 * @param apellidoInput
	 * @param apellidoPersona
	 * @param claveInput
	 * @param clavePersona
	 * @param cantPersonas
	 */
	private static void registrarNuevoUsuario(String rutInput, String[] rutPersona, String nombreInput,
	String[] nombrePersona, String apellidoInput, String[] apellidoPersona, String claveInput,
	String[] clavePersona, int cantPersonas) {
		rutPersona[cantPersonas] = rutInput;
		nombrePersona[cantPersonas] = nombreInput;
		apellidoPersona[cantPersonas] = apellidoInput;
		clavePersona[cantPersonas] = claveInput;
		System.out.println("\nRegistro exitoso.");
	}

	/**
	 * Boolean Function that returns true if rutInput is found on the vector rutPersona, else, false
	 * @param rutInput
	 * @param rutPersona
	 * @param cantPersonas
	 * @return true if person is registed, else, returns false
	 */
	private static Boolean buscarRut(String rutInput, 
	String[] rutPersona, int cantPersonas) {
		int index_rut = Index(rutPersona, rutInput, cantPersonas);
		//RUT ENCONTRADO
		if ((index_rut != -1) || (rutInput.equals("ADMIN"))) {
			return true;
		} 
		//RUT NO ENCONTRADO
		else {
			return false;
		}
	}

	/**
	 * Function thar reads .txt file and returns quantity
	 * @param nombrePersona
	 * @param apellidoPersona
	 * @param rutPersona
	 * @param clavePersona
	 * @param saldoPersona
	 * @param inventarioPersona
	 * @param cantProductos
	 * @param producto
	 * @return quantity of unique people found in .txt file
	 * @throws IOException
	 */
	private static int lecturaRellenadoPersonas(String[] nombrePersona, 
	String[] apellidoPersona, String[] rutPersona,
	String[] clavePersona, int[] saldoPersona, int[][] inventarioPersona, 
	int cantProductos, String[] producto) throws IOException{
		File file = new File("personas.txt");
        Scanner scan = new Scanner(file);
        int contador_personas = 0;
        while(scan.hasNextLine()) {
        	String linea = scan.nextLine();
            String[] partes = linea.split(",");
            String nom = partes[0];
            String apellido = partes[1];
            String rut = partes[2];
            rut = cambiaFormato(rut);
            String clave = partes[3];
            int saldo = Integer.parseInt(partes[4]);
            
            int index_persona = Index(rutPersona, rut, contador_personas);
            
            for (int i = 5; i < partes.length; i++) {
            	
            	String tiene = partes[i];
            	int index_producto = Index(producto, tiene, cantProductos);
     
            	if (index_persona == -1) {
            		inventarioPersona[contador_personas][index_producto] += 1;
   
				} 
            	else {
					inventarioPersona[index_persona][index_producto] += 1;

				}
			}
     
        	//PERSONA NUEVA
            if (index_persona == -1) {
            	nombrePersona[contador_personas] = nom;
            	apellidoPersona[contador_personas] = apellido;
            	rutPersona[contador_personas] = rut;
            	clavePersona[contador_personas] = clave;
            	saldoPersona[contador_personas] = saldo;
 
            	contador_personas ++;
			} 
          //PERSONA ENCONTRADA
            else {
            	nombrePersona[index_persona] = nom;
            	apellidoPersona[index_persona] = apellido;
            	rutPersona[index_persona] = rut;
            	clavePersona[index_persona] = clave;
            	saldoPersona[index_persona] = saldo;                	            
                }	
			}
        	scan.close();
        	return contador_personas;
	}

	/**
	 * Function that returns formated string
	 * @param rut
	 * @return a string without ".", "," and ignores letter case
	 */
	private static String cambiaFormato(String rut) {
		String[] lista = rut.split("");
		String retorno = "";
		for (int i = 0; i < lista.length; i++) {
			if (lista[i].equals(".") || lista[i].equals("-")) {	
			} 
			else {
				if (lista[i].equalsIgnoreCase("k")) {
					retorno += "k";
				} 
				else {
					retorno+=lista[i];
				}
			}
		}
		return retorno;
	}

	/**
	 * Function that performs the reading of the .txt file and returns how many products are in the file
	 * @param tipoProducto
	 * @param producto
	 * @param stockProducto
	 * @param precioStockProducto
	 * @param tienda
	 * @param cantTiendas
	 * @return quantity of products found in the .txt file
	 * @throws IOException
	 */
	private static int lecturaRellenadoProductos(String[] tipoProducto, 
    String[] producto, int[][] stockProducto, int[][] precioStockProducto, 
    String[] tienda, int cantTiendas) throws IOException{
		File file = new File("productos.txt");
        Scanner scan = new Scanner(file);
        int contador_productos = 0;
        while(scan.hasNextLine()) {
        	String linea = scan.nextLine();
            String[] partes = linea.split(",");
            String comic_manga = partes[0];
            String nom_producto = partes[1];
            int stock_en_tienda = Integer.parseInt(partes[2]);
            int valor_en_tienda = Integer.parseInt(partes[3]);
            String nom_tienda = partes[4];
            
            int index_tienda = Index(tienda, nom_tienda, cantTiendas);
            int index_producto = Index(producto, nom_producto, contador_productos);
            
            //PRODUCTO NUEVO
            if (index_producto == -1) {
            	tipoProducto[contador_productos] = comic_manga;
            	producto[contador_productos] = nom_producto;
            	//fila|columnacaca
            	stockProducto[index_tienda][contador_productos] = stock_en_tienda;
            	precioStockProducto[index_tienda][contador_productos] = valor_en_tienda;
            	
            	contador_productos ++;
			} 
            //PRODUCTO ENCONTRADO
            else {
				tipoProducto[index_producto] = comic_manga;
				producto[index_producto] = nom_producto;
				//fila|columna
				stockProducto[index_tienda][index_producto] = stock_en_tienda;
				precioStockProducto[index_tienda][index_producto] = valor_en_tienda;	
					} 
			}
        scan.close();
		return contador_productos;
	}

	/**
	 * Function that returns the index of a string element on a list of strings
	 * @param lista
	 * @param cosa
	 * @param cantidad
	 * @return index of element in list
	 */
	private static int Index(String[] lista, String cosa, int cantidad) {
		if (cantidad == 0) {
			return -1;	
		}
		
		for (int i = 0; i < cantidad; i++) {
			if (lista[i].equals(cosa)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Function that performs the reading of the file and returns the how many stores are in the file
	 * @param tienda 
	 * @param montoRecTienda
	 * @return quantity of individual stores in a .txt file
	 * @throws IOException
	 */
	private static int lecturaRellenadoTiendas(String[] tienda, 
	int[] montoRecTienda) throws IOException{
		File file = new File("tiendas.txt");
	      Scanner scan = new Scanner(file);
	      int contador = 0;
	      while(scan.hasNextLine()){
	          String linea = scan.nextLine();
	          String[] partes = linea.split(",");
	          tienda[contador] = partes[0];
	          montoRecTienda[contador] = Integer.parseInt(partes[1]);
	          contador++;
	      }
	      scan.close();
	      return contador;
	}

}
