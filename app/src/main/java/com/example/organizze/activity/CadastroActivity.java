package com.example.organizze.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.organizze.R;
import com.example.organizze.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.example.organizze.config.ConfiguracaoFirebase;
import com.google.firebase.database.core.Tag;

public class CadastroActivity extends AppCompatActivity {

    private EditText campoNome, campoEmail, campoSenha;
    private Button botaoCadastrar;
    private FirebaseAuth autenticacao;
    private Usuario usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        campoNome =  findViewById(R.id.editNome);
        campoEmail =  findViewById(R.id.editEmail);
        campoSenha =  findViewById(R.id.editSenha);
        botaoCadastrar = findViewById(R.id.botaoCadastrar);

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 if(erroMensagem().isEmpty()) {
                     usuario =  new Usuario();
                     usuario.setNome(campoNome.getText().toString());
                     usuario.setEmail(campoEmail.getText().toString());
                     usuario.setSenha(campoSenha.getText().toString());
                     cadastrarUsuario();
                 }else {
                     Toast.makeText(CadastroActivity.this, "O campo não está preenchido" + erroMensagem(), Toast.LENGTH_SHORT).show();
                 }
            }
        });

    }

    private void cadastrarUsuario() {
        autenticacao =  ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(CadastroActivity.this, "Sucesso ao cadastrar usuário" + task.getException(), Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(CadastroActivity.this, "Erro ao cadastrar usuário", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    private String erroMensagem() {
       String variavelNome = "";
       if (campoNome.getText().toString().isEmpty()) variavelNome += " ,Nome" ;
       if (campoEmail.getText().toString().isEmpty()) variavelNome += " ,Email" ;
       if (campoSenha.getText().toString().isEmpty()) variavelNome += " ,Senha" ;
      return variavelNome;
    }
}