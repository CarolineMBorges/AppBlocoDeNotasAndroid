package preferenciascorusuario.cursoandroid.com.minhasanotacoes;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends Activity {

    private EditText texto;
    private ImageView botaoSalvar;
    private static final String NOME_ARQUIVO = "arquivo_anotacao.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        texto = findViewById(R.id.textoId);
        botaoSalvar = findViewById(R.id.botaoSalvarId);

        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String textoDigitado = texto.getText().toString();
                gravarNoArquivo(textoDigitado);
                Toast.makeText(MainActivity.this, "Anotação salva com sucesso", Toast.LENGTH_SHORT).show();
            }
        });

        //recuperar o que foi gravado
        if (lerArquivo() != null){
            texto.setText( lerArquivo() );
        }
    }

    private void gravarNoArquivo(String texto){
        try{

            /*OutputStream é capaz de enviar dados a um determinado Stream, ao contrário do InputStream que faz a leitura do mesmo.
            * Um programa que precise escrever um dado em algum local (destino) precisa de um OutputStreamWriter.
            */

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter( openFileOutput(NOME_ARQUIVO, Context.MODE_PRIVATE));
            outputStreamWriter.write( texto );
            outputStreamWriter.close();

        }catch(IOException e){
            Log.v("MainActivity", e.toString());
        }
    }


    private String lerArquivo(){
        String resultado = "";

        try{
            //Abrir o arquivo
            /*
            * O InputStream faz parte da leitura de dados, ou seja, está conectado a alguma fonte de dados: arquivo,
            * conexão de internet, vídeo e etc. O InputStream nos possibilita ler esse Stream em byte, um byte por vez.
            * */
            InputStream arquivo = openFileInput(NOME_ARQUIVO);
            if(arquivo != null){

                //ler o arquivo

                /*
                *  InputStreamReader usa caracteres
                *  Um programa que precise ler algum dado de algum local (uma fonte) precisa de um InputStreamReader
                * */
                InputStreamReader inputStreamReader = new InputStreamReader( arquivo );

                //gerar Buffer do arquivo lido
                BufferedReader bufferedReader = new BufferedReader( inputStreamReader );

                //recuperar textos do arquivo
                String linhaArquivo = "";
                while( (linhaArquivo = bufferedReader.readLine()) != null){
                    resultado += linhaArquivo;
                }

                //fechar o arquivo
                arquivo.close();
            }

        }catch (IOException e){
            Log.v("MainActivity", e.toString());
        }

        return resultado;
    }
}
