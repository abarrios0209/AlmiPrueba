package com.prueba.almi.utilitarios;

public class Constantes {

    private Constantes(){}

    public static class CredencialesError {
        private CredencialesError(){}

        public static final String TOKEN_NO_VALIDO = "Token no valido";
        public static final String CREDENCIALES_INVALIDAS_LOGIN = "Credenciales inválidas";
        public static final String NO_HAY_CLIENTE_PARA_ELIMINAR = "No existe ningun cliente para eliminar";
        public static final String ERROR_ACTUALIZAR_CLIENTE = "Error al actualizar el cliente";
        public static final String EL_CLIENTE_YA_EXISTE = "El cliente ya se encuentra registrado, con ese numero de cedula";
        public static final String CLIENTE_NO_EXISTE_PARA_CREDITO = "El cliente que quieres crear el credito no existe";
        public static final String NO_SE_ENCONTRO_CREDITO_A_PAGAR = "No se encontro credito a pagar";
        public static final String EL_CREDITO_YA_ESTA_PAGO = "El credito ya esta pago";
        public static final String CUOTA_NO_EXISTE_O_PAGADA = "La cuota no existe o ya ha sido pagada";

    }

    public static class ClienteRegistrado {
        private ClienteRegistrado(){}

        public static final String CLIENTE_CREADO = "Cliente creado exitosamente";
    }

    public static class ComplementosRespuesta{
        private ComplementosRespuesta(){}

        public static final String CAMBIO_ESTADO = "El estado a cambiado a ";
        public static final String NO_CAMBIO_ESTADO_CLIENTE = "No se ha podido cambiar el estado o no existe el cliente";
        public static final String DEFINIR_TIPO_RIESGO_1 = "El cliente registrado con la identificacion: ";
        public static final String DEFINIR_TIPO_RIESGO_2 = " no se encuentra registrado";

    }

    public static class TiposDeRiesgo{
        private TiposDeRiesgo(){}

        public static final String TIPO_A = "tipo A";
        public static final String TIPO_B = "tipo B";
        public static final String TIPO_C = "tipo C";
    }

    public static class EstadosCreditoYCuotas{
        private EstadosCreditoYCuotas(){}

        public static final String PENDIENTE = "PENDIENTE";
        public static final String PAGADO = "PAGADO";
        public static final String PAGADA = "PAGADA";
    }
}
