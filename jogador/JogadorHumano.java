package jogador;

import jogo.Marca;
import jogo.Tabuleiro;
import java.io.*;

public class JogadorHumano implements Jogador, Cloneable
{
    private Marca          marca     = null;
    private Tabuleiro      tabDoJogo = null;
    // O mesmo leitor preserva as entradas já armazenadas entre as partidas.
    private static final BufferedReader TECLADO = new BufferedReader
                                                 (new InputStreamReader
                                                 (System.in));

    public JogadorHumano (Tabuleiro tab) throws Exception
    {
        if (tab==null) throw new Exception ("Tabuleiro ausente");

        // Os participantes e o analista acompanham o mesmo tabuleiro da partida.
        this.tabDoJogo = tab;
        while (this.marca==null)
        {
            System.out.println ("Escolha sua marca (X/O):");
            String resposta = JogadorHumano.leiaLinha ();
            if (resposta.equalsIgnoreCase ("X"))
                this.marca = new Marca ('X');
            else if (resposta.equalsIgnoreCase ("O"))
                this.marca = new Marca ('O');
            else
                System.err.println ("Marca inválida. Digite X ou O.");
        }
    }

    public JogadorHumano (Tabuleiro tab, Marca mrc) throws Exception
    {
        if (tab==null) throw new Exception ("Tabuleiro ausente");
        if (mrc==null) throw new Exception ("Marca ausente");

        this.tabDoJogo = tab;
        this.marca = mrc;
    }

    // Mantém as assinaturas públicas e encaminha falhas de entrada ao principal.
    private static String leiaLinha ()
    {
        try
        {
            String ret = JogadorHumano.TECLADO.readLine ();
            if (ret==null) throw new IllegalStateException ("Entrada encerrada");
            return ret.trim ();
        }
        catch (IOException erro)
        {
            throw new IllegalStateException ("Não foi possível ler a entrada", erro);
        }
    }

    @Override
    public Marca getMarca ()
    {
        return this.marca;
    }

    @Override
    public void facaSuaJogada ()
    {
        System.out.println (this.tabDoJogo);
        System.out.println ("Sua marca: "+this.marca);

        while (true)
        {
            System.out.println ("Linha (0 a 2):");
            String linha = JogadorHumano.leiaLinha ();
            System.out.println ("Coluna (0 a 2):");
            String coluna = JogadorHumano.leiaLinha ();

            int lin, col;
            try
            {
                lin = Integer.parseInt (linha);
                col = Integer.parseInt (coluna);
            }
            catch (NumberFormatException erro)
            {
                System.err.println ("Coordenadas inválidas. Digite números de 0 a 2.");
                continue;
            }

            if (lin<0 || lin>2 || col<0 || col>2)
            {
                System.err.println ("Coordenadas inválidas. Digite números de 0 a 2.");
                continue;
            }

            try
            {
                if (this.tabDoJogo.haMarcaNaPosicao (lin, col))
                {
                    System.err.println ("Posição ocupada. Tente novamente.");
                    continue;
                }

                this.tabDoJogo.setMarcaNaPosicao (this.marca, lin, col);
                return;
            }
            catch (Exception erro)
            {
                throw new IllegalStateException ("Não foi possível realizar a jogada", erro);
            }
        }
    }

    public void vcGanhou ()
    {
        System.out.println ("Você ganhou!");
    }

    public void vcPerdeu ()
    {
        System.out.println ("Você perdeu!");
    }

    public void deuVelha ()
    {
        System.out.println ("Deu velha! Empate.");
    }

    public boolean querMaisUmaPartida ()
    {
        while (true)
        {
            System.out.println ("Quer mais uma partida (S/N)?");
            try
            {
                String resposta = JogadorHumano.TECLADO.readLine ();
                if (resposta==null) return false;
                resposta = resposta.trim ();

                if (resposta.equalsIgnoreCase ("S")) return true;
                if (resposta.equalsIgnoreCase ("N")) return false;
                System.err.println ("Resposta inválida. Digite S ou N.");
            }
            catch (IOException erro)
            {
                System.err.println ("Não foi possível ler a resposta: "+erro.getMessage ());
                return false;
            }
        }
    }

    @Override
    public String toString ()
    {
        return "Jogador humano\nMarca: "+this.marca+"\nTabuleiro:\n"+this.tabDoJogo;
    }

    @Override
    public boolean equals (Object obj)
    {
        if (this==obj) return true;
        if (obj==null) return false;
        if (this.getClass()!=obj.getClass()) return false;

        JogadorHumano jogador = (JogadorHumano)obj;
        return this.marca.equals (jogador.marca) && this.tabDoJogo.equals (jogador.tabDoJogo);
    }

    @Override
    public int hashCode ()
    {
        int ret = 1;
        ret = 2*ret + this.marca.hashCode ();
        ret = 2*ret + this.tabDoJogo.hashCode ();

        if (ret<0) ret = -ret;
        if (ret<0) ret = 0;
        return ret;
    }

    // Construtor de cópia: o leitor é compartilhado, mas o tabuleiro não.
    public JogadorHumano (JogadorHumano modelo) throws Exception
    {
        if (modelo==null) throw new Exception ("Modelo ausente");

        this.marca = modelo.marca;
        this.tabDoJogo = new Tabuleiro (modelo.tabDoJogo);
    }

    @Override
    public Object clone ()
    {
        try
        {
            return new JogadorHumano (this);
        }
        catch (Exception erro)
        {
            throw new IllegalStateException ("Não foi possível copiar o jogador humano", erro);
        }
    }
}
