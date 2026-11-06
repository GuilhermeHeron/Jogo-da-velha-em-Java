package jogo;

public class Marca
{
    private char simbolo;

    public Marca (char smb) throws Exception
    {
        if (smb!='X' && smb!='O')
            throw new Exception ("Símbolo inválido");

        this.simbolo = smb;
    }

    public char getSimbolo ()
    {
        return this.simbolo;
    }

    @Override
    public String toString ()
    {
        return ""+simbolo;
    }

    @Override
    public boolean equals (Object obj)
    {
        if (this==obj) return true;
        if (obj==null) return false;
        if (this.getClass()!=obj.getClass()) return false;

        Marca marca = (Marca)obj;
        return this.simbolo==marca.simbolo;
    }

    @Override
    public int hashCode ()
    {
        int ret = 1;
        ret = 2*ret + ((Character)this.simbolo).hashCode();

        if (ret<0) ret = -ret;
        if (ret<0) ret = 0;
        return ret;
    }
}
