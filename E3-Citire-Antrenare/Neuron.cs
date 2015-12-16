using System;
using System.Collections.Generic;
using System.Globalization;
using System.IO;
using System.Linq;

namespace RNpredictivClass
{
    public class Neuron
    {
        private double[] w;
        private double[] val;
        private const double Eta = 0.01;
        private readonly int k;

        #region Antrenare

        public Neuron(int n)
        {
            k = n;
        }

        public void SetValues(string filename,double range)
        {
            val = Read(filename);
            w = GenerateRandom(k, range);
        }

        public void Antrenare()
        {
            double eg;
            double global = 0;
            var output = new double[val.Length];
            do
            {
                for (int i = 0; i < val.Length - k; i++)
                {
                    var fer = val.Skip(i).Take(k).ToArray();
                    var result = fer.Zip(w, (a, b) => a*b).ToArray().Sum();
                    var err = result - val[i + 1];

                    for (int j = 0; j < k; j++)
                    {
                        w[j] = w[j] - Eta*val[j]*err;
                    }
                }
                eg = 0;
                for (int i = 0; i < val.Length - k; i++)
                {
                    var fer = val.Skip(i).Take(k).ToArray();
                    var result = fer.Zip(w, (a, b) => a*b).ToArray().Sum();
                    var err = result - val[i + 1];
                    eg = eg + Math.Abs(err);
                    output[i] = result;
                }
                global = (global + eg)/2;
            } while (Math.Abs(eg - global) > 0);
            Write(output,"Output.txt");
        }

        private double[] GenerateRandom(int length, double range)
        {
            double[] r = new double[length];

            for (int i = 0; i < length; i++)
            {
                r[i] = Rand(range);
            }
            return r;
        }

        private double Rand(double range)
        {
            Random random = new Random();
            return random.NextDouble() * (range - (-1 * range)) + (-1 * range);
        }

        private double[] Read(string filename)
        {
            List<double> values = new List<double>();
            using (TextReader reader = File.OpenText(filename))
            {
                string line;
                while ((line = reader.ReadLine()) != null)
                {
                    try
                    {
                        values.Add(double.Parse(line, CultureInfo.InvariantCulture));
                    }
                    catch (Exception e)
                    {
                        Console.WriteLine(e.Message);
                    }
                }
            }
            return values.ToArray();
        }

        #endregion

        #region ParsareWav
        public void ParseWav(string filename)
        {
            BinaryReader reader = new BinaryReader(new MemoryStream(File.ReadAllBytes(filename)));
            int chunkId = reader.ReadInt32();
            int fileSize = reader.ReadInt32();
            int riffType = reader.ReadInt32();
            int fmtId = reader.ReadInt32();
            int fmtSize = reader.ReadInt32();
            int fmtCode = reader.ReadInt16();
            int fmtChannels = reader.ReadInt16();
            int fmtSampleRate = reader.ReadInt32();
            int fmtAvgBps = reader.ReadInt32();
            int fmtBlockAlign = reader.ReadInt16();
            int fmtBitDepth = reader.ReadInt16();

            if (fmtSize == 18)
            {
                int fmtExtraSize = reader.ReadInt16();
                reader.ReadBytes(fmtExtraSize);
            }

            int dataId = reader.ReadInt32();
            int dataSize = reader.ReadInt32();
            double[] data;
            byte[] dataBytes = reader.ReadBytes(dataSize);

            if (fmtChannels.Equals(2))
                dataBytes = StereoToMono(dataBytes);

            var color = Console.ForegroundColor;

            if (!fmtSampleRate.Equals(16000))
            {
                Console.ForegroundColor = ConsoleColor.Red;
                Console.WriteLine("Recommended 16khz wav file,this is a {0}khz wav file.", fmtSampleRate/1000);
                Console.ForegroundColor = color;
                return;
            }

            Console.WriteLine("chunkID :" + chunkId);
            Console.WriteLine("fileSize: " + fileSize);
            Console.WriteLine("SampleRate: " + fmtSampleRate);
            Console.WriteLine("BitDepth: " + fmtBitDepth);
            Console.WriteLine("Channels: " + fmtChannels);

            if (fmtBitDepth.Equals(16))
            {
                data = new double[dataSize / 2];
                for (int n = 0; n < dataSize / 2; n++)
                {
                    data[n] = BitConverter.ToInt16(dataBytes, n * 2) / 32768f;
                }
                try
                {
                    Write(data,"DataSet.txt");
                    Console.ForegroundColor = ConsoleColor.Yellow;
                    Console.WriteLine("Output written!");
                    Console.ForegroundColor = color;
                }
                catch (Exception e)
                {
                    Console.ForegroundColor = ConsoleColor.Red;
                    Console.WriteLine(e.Message);
                    Console.ForegroundColor = color;
                }
            }  
        }

        private static byte[] StereoToMono(byte[] input)
        {
            byte[] output = new byte[input.Length / 2];
            int outputIndex = 0;
            for (int n = 0; n < input.Length; n += 4)
            {
                output[outputIndex++] = input[n];
                output[outputIndex++] = input[n + 1];
            }
            return output;
        }

        #endregion

        private void Write(IEnumerable<double> output, string filename)
        {
            StreamWriter file;
            using (file = new StreamWriter(@filename))
            {
                foreach (var d in output)
                {
                    file.WriteLine(d.ToString(CultureInfo.InvariantCulture));
                }
            }
        }
    }
}
