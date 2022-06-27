package com.vr.miniautorizador.erros;

import com.vr.miniautorizador.domain.cartao.Cartao;
import lombok.Getter;

@Getter
public class ExceptionDetails {
    private String title;
    private int status;
    private ExceptionBody body;

    private ExceptionDetails() {
    }

    public static final class ExceptionDetailsBuilder {
        private String title;
        private int status;
        private ExceptionBody body;

        private ExceptionDetailsBuilder() {
        }

        public static ExceptionDetailsBuilder newBuilder() {
            return new ExceptionDetailsBuilder();
        }

        public ExceptionDetailsBuilder title(String title) {
            this.title = title;
            return this;
        }

        public ExceptionDetailsBuilder status(int status) {
            this.status = status;
            return this;
        }

        public ExceptionDetailsBuilder body(ExceptionBody body) {
            this.body = body;
            return this;
        }

        public ExceptionDetails build() {
            ExceptionDetails exceptionDetails = new ExceptionDetails();
            exceptionDetails.title = this.title;
            exceptionDetails.status = this.status;
            exceptionDetails.body = this.body;
            return exceptionDetails;
        }
    }
}
