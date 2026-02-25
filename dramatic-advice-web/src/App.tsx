import { useState } from "react";
import "./App.css";

export default function App() {
  const [text, setText] = useState("");
  const [advice, setAdvice] = useState("");
  const [loading, setLoading] = useState(false);

  async function generateAdvice() {
    if (!text.trim()) return alert("Write something first!");

    setLoading(true);
    try {
      const res = await fetch("http://localhost:8080/api/advice", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ text })
      });

      const data = await res.json();
      setAdvice(data.advice);
    } catch (err) {
      console.error(err);
      alert("Backend not responding!");
    }
    setLoading(false);
  }

  return (
    <div className="container">
      <h1>✨ Dramatic Advice Generator</h1>

      <textarea
        placeholder="Write your daily thought..."
        value={text}
        onChange={(e) => setText(e.target.value)}
      />

      <button onClick={generateAdvice} disabled={loading}>
        {loading ? "Summoning Wisdom..." : "Generate Advice"}
      </button>

      {advice && <div className="advice-box">{advice}</div>}
    </div>
  );
}