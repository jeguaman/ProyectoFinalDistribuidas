package com.teamj.distribuidas.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jose Guaman
 */
public class ValidationUtil {

    public static Boolean soloLetras(String str) {
        return str.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ]+");
    }

    public static Boolean soloNumeros(String str) {
        return str.matches("[0-9]+");
    }

    public static Boolean soloLetrasYNumeros(String str) {
        return str.matches("[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ]+");
    }

    public static Boolean soloLetrasSeparadas(String str) {
        return str.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+");
    }

    public static Boolean soloLetrasNumerosSeparadas(String str) {
        return str.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ0-9\\s\\.]+");
    }

    public static Boolean soloLetrasNumerosSeparadasCiertosCaracteres(String str) {
        return str.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ0-9\\s\\.\\,\\_\\-\\+\\/\\*\\#]+");
    }

    public static Boolean soloCorreoElectronico(String str) {
        return str.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$");
    }

    public static Boolean longitud(String str, int min, int max) {
        int size = str.length();
        return size >= min && size <= max;
    }

    public static Boolean soloNumerosDecimales(String str) {
        return str.matches("^[0-9]+[\\.]{0,1}([0-9]{1,6})?$");
    }

    public static boolean validarDocumentoEcuador(String numero) {
        boolean valor = true;
        try {
            int suma = 0;
            int residuo = 0;
            boolean privada = false;
            boolean publica = false;
            boolean natural = false;
            int numeroProvincias = 24;
            int digitoVerificador = 0;
            int modulo = 11;
            int d1, d2, d3, d4, d5, d6, d7, d8, d9, d10;
            int p1, p2, p3, p4, p5, p6, p7, p8, p9;
            d1 = d2 = d3 = d4 = d5 = d6 = d7 = d8 = d9 = d10 = 0;
            p1 = p2 = p3 = p4 = p5 = p6 = p7 = p8 = p9 = 0;

            if (numero.length() < 10) {
                valor = false;
            }

            // Los primeros dos digitos corresponden al codigo de la provincia
            int provincia = Integer.parseInt(numero.substring(0, 2));

            if (provincia <= 0 || provincia > numeroProvincias) {
                valor = false;
            }

            // Aqui almacenamos los digitos de la cedula en variables.
            d1 = Integer.parseInt(numero.substring(0, 1));
            d2 = Integer.parseInt(numero.substring(1, 2));
            d3 = Integer.parseInt(numero.substring(2, 3));
            d4 = Integer.parseInt(numero.substring(3, 4));
            d5 = Integer.parseInt(numero.substring(4, 5));
            d6 = Integer.parseInt(numero.substring(5, 6));
            d7 = Integer.parseInt(numero.substring(6, 7));
            d8 = Integer.parseInt(numero.substring(7, 8));
            d9 = Integer.parseInt(numero.substring(8, 9));
            d10 = Integer.parseInt(numero.substring(9, 10));

            // El tercer digito es:
            // 9 para sociedades privadas y extranjeros
            // 6 para sociedades publicas
            // menor que 6 (0,1,2,3,4,5) para personas naturales
            if (d3 == 7 || d3 == 8) {
                valor = false;
            }

            // Solo para personas naturales (modulo 10)
            if (d3 < 6) {
                natural = true;
                modulo = 10;
                p1 = d1 * 2;
                if (p1 >= 10) {
                    p1 -= 9;
                }
                p2 = d2 * 1;
                if (p2 >= 10) {
                    p2 -= 9;
                }
                p3 = d3 * 2;
                if (p3 >= 10) {
                    p3 -= 9;
                }
                p4 = d4 * 1;
                if (p4 >= 10) {
                    p4 -= 9;
                }
                p5 = d5 * 2;
                if (p5 >= 10) {
                    p5 -= 9;
                }
                p6 = d6 * 1;
                if (p6 >= 10) {
                    p6 -= 9;
                }
                p7 = d7 * 2;
                if (p7 >= 10) {
                    p7 -= 9;
                }
                p8 = d8 * 1;
                if (p8 >= 10) {
                    p8 -= 9;
                }
                p9 = d9 * 2;
                if (p9 >= 10) {
                    p9 -= 9;
                }
            }

            // Solo para sociedades publicas (modulo 11)
            // Aqui el digito verficador esta en la posicion 9, en las otras 2
            // en la pos. 10
            if (d3 == 6) {
                publica = true;
                p1 = d1 * 3;
                p2 = d2 * 2;
                p3 = d3 * 7;
                p4 = d4 * 6;
                p5 = d5 * 5;
                p6 = d6 * 4;
                p7 = d7 * 3;
                p8 = d8 * 2;
                p9 = 0;
            }

            /* Solo para entidades privadas (modulo 11) */
            if (d3 == 9) {
                privada = true;
                p1 = d1 * 4;
                p2 = d2 * 3;
                p3 = d3 * 2;
                p4 = d4 * 7;
                p5 = d5 * 6;
                p6 = d6 * 5;
                p7 = d7 * 4;
                p8 = d8 * 3;
                p9 = d9 * 2;
            }

            suma = p1 + p2 + p3 + p4 + p5 + p6 + p7 + p8 + p9;
            residuo = suma % modulo;

            // Si residuo=0, dig.ver.=0, caso contrario 10 - residuo
            digitoVerificador = residuo == 0 ? 0 : modulo - residuo;
            int longitud = numero.length(); // Longitud del string

            // ahora comparamos el elemento de la posicion 10 con el dig. ver.
            if (publica == true) {
                if (digitoVerificador != d9) {
                    valor = false;
                }
                /* El ruc de las empresas del sector publico terminan con 0001 */
                if (!numero.substring(9, longitud).equals("0001")) {
                    valor = false;
                }
            }

            if (privada == true) {
                if (digitoVerificador != d10) {
                    //numero.setText("");
                    valor = false;
                }
                if (!numero.substring(10, longitud).equals("001")) {
                    //numero.setText("");
                    valor = false;
                }
            }

            if (natural == true) {
                if (digitoVerificador != d10) {
                    valor = false;
                }
                if (numero.length() > 10 && !numero.substring(10, longitud).equals("001")) {
                    valor = false;
                }
            }
        } catch (Exception e) {
            valor = false;
        }
        return valor;
    }

    public static Date getFechaYHora() {
        Date date = new Date();
        Calendar calendario = Calendar.getInstance();
        try {
            String anio = String.valueOf(calendario.get(Calendar.YEAR));
            String mes = String.valueOf(calendario.get(Calendar.MONTH));
            String dia = String.valueOf(calendario.get(Calendar.DAY_OF_MONTH));
            String hora = String.valueOf(calendario.get(Calendar.HOUR_OF_DAY));
            String minuto = String.valueOf(calendario.get(Calendar.MINUTE));
            String segundo = String.valueOf(calendario.get(Calendar.SECOND));
            String fechaActual = anio + "-" + mes + "-" + dia + " " + hora + ":" + minuto + ":" + segundo;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = sdf.parse(fechaActual);
        } catch (ParseException ex) {
            Logger.getLogger(ValidationUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return date;
    }

}
