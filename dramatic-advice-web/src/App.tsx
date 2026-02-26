import { useState, useEffect, useRef } from "react";
import "./App.css";

export default function App() {

  const moodOptions = [
    {
      name: "Sweet",
      emoji: "💖",
      bg: "linear-gradient(135deg, #ffe6f0, #fff0f5)",
      color: "#ff6f91",
      particle: "petal"
    },
    {
      name: "Angry",
      emoji: "😡",
      bg: "linear-gradient(135deg, #ff9a9e, #ff4e50)",
      color: "#ff2e2e",
      particle: "fire"
    },
    {
      name: "Nonchalant",
      emoji: "😌",
      bg: "linear-gradient(135deg, #e0eafc, #cfdef3)",
      color: "#5a8dee",
      particle: "bubble"
    },
    {
      name: "Savage",
      emoji: "🔥",
      bg: "linear-gradient(135deg, #f7971e, #ff0844)",
      color: "#ff4500",
      particle: "ember"
    }
  ];

  const [text, setText] = useState("");
  const [advice, setAdvice] = useState("");
  const [loading, setLoading] = useState(false);
  const [mood, setMood] = useState(moodOptions[0]);
  const [showMoodMenu, setShowMoodMenu] = useState(false);

  const moodRef = useRef<HTMLDivElement>(null);

  // 🌸 Handle background + outside click
  useEffect(() => {

    // background shift
    document.body.style.background = mood.bg;
    document.body.style.transition = "background 0.6s ease";

    function handleClickOutside(event: MouseEvent) {
      if (moodRef.current && !moodRef.current.contains(event.target as Node)) {
        setShowMoodMenu(false);
      }
    }

    document.addEventListener("mousedown", handleClickOutside);

    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };

  }, [mood]);

  // 🌸 Petal animation (separate effect)
  useEffect(() => {
    const container = document.querySelector(".particle-container");
    if (!container) return;

    container.innerHTML = "";

    function createParticle() {
      const particle = document.createElement("div");
      particle.classList.add("particle", mood.particle);

      particle.style.left = Math.random() * 100 + "vw";
      particle.style.animationDuration = 4 + Math.random() * 4 + "s";

      container.appendChild(particle);

      setTimeout(() => {
        particle.remove();
      }, 8000);
    }

    const interval = setInterval(createParticle, 300);
    return () => clearInterval(interval);

  }, [mood]);

  async function generateAdvice() {
    if (!text.trim()) {
      alert("Write something first!");
      return;
    }

    setLoading(true);

    try {
      const res = await fetch("http://localhost:8080/api/advice", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ text, mood: mood.name })
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
    <>
      <div className="particle-container"></div>

      {/* Mood Selector - Top Right */}
      <div className="mood-global-wrapper" ref={moodRef}>
        <button
          className="mood-button"
          style={{ backgroundColor: mood.color }}
          onClick={() => setShowMoodMenu(!showMoodMenu)}
        >
          {mood.emoji} {mood.name} ▼
        </button>

        {showMoodMenu && (
          <div className="mood-dropdown">
            {moodOptions.map((m) => (
              <div
                key={m.name}
                className="mood-item"
                onClick={() => {
                  setMood(m);
                  setShowMoodMenu(false);
                }}
              >
                {m.emoji} {m.name}
              </div>
            ))}
          </div>
        )}
      </div>

      <div
        className={`app-container ${advice ? "expanded" : ""}`}
        style={{ boxShadow: `0 25px 60px ${mood.color}66` }}
      >
        <h2 className="title" style={{ color: mood.color }}>
          {mood.emoji} Dramatic Advice Generator
        </h2>

        <textarea
          placeholder="Write your daily thought..."
          value={text}
          onChange={(e) => setText(e.target.value)}
        />

        <button
          onClick={generateAdvice}
          disabled={loading}
          style={{ backgroundColor: mood.color }}
        >
          {loading ? "Generating..." : "Generate Advice"}
        </button>

        {advice && (
          <div className="advice-box">
            {advice}
          </div>
        )}
      </div>
    </>
  );
}