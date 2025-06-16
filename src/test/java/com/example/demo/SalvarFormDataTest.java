package com.example.demo;

import com.example.demo.FeedBack.SalvarFormData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SalvarFormDataTest {

    @Test
    void deveSalvar15FeedbacksDiferentes() {
        SalvarFormData salvarFormData = new SalvarFormData();

        for (int i = 1; i <= 15; i++) {
            int nota = (i % 5) + 1;  // notas de 1 a 5, alternando
            String comentario = "Comentário de teste " + i;
            boolean reparo = (i % 2 == 0);  // alterna entre true e false
            int bikeId = 100 + i;  // ids únicos de bike (ex: 101, 102, ...)
            String nome = "Usuário " + i;

            // Salvar no banco
            assertDoesNotThrow(() -> salvarFormData.Save(nota, comentario, reparo, bikeId, nome),
                    "Falha ao salvar o feedback número: " + i);
        }
    }
}

