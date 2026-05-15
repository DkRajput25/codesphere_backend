/** @type {import('tailwindcss').Config} */
export default {
  darkMode: "class",
  content: ["./index.html", "./src/**/*.{js,jsx}"],
  theme: {
    extend: {
      colors: {
        canvas: {
          50: "#f7f8fb",
          100: "#eef1f8",
          900: "#0f172a",
          950: "#060816"
        }
      },
      boxShadow: {
        panel: "0 18px 60px rgba(15, 23, 42, 0.18)"
      },
      backgroundImage: {
        "hero-grid":
          "radial-gradient(circle at top left, rgba(14,165,233,0.24), transparent 28%), radial-gradient(circle at bottom right, rgba(56,189,248,0.18), transparent 22%), linear-gradient(135deg, rgba(15,23,42,1), rgba(8,47,73,0.92))"
      },
      fontFamily: {
        display: ["Sora", "sans-serif"],
        body: ["Manrope", "sans-serif"],
        mono: ["JetBrains Mono", "monospace"]
      }
    }
  },
  plugins: []
};
