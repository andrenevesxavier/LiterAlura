package com.desafio.LiterAlura.Service;

public interface IConverteDados {
    <T> T  obterDados(String json, Class<T> classe);
}
