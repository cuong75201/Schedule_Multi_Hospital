import { useEffect, useState } from "react";
import { useLocation } from "react-router";
import HeroSection from "../../components/sections/HeroSection";
import type { SearchRoute } from "../../components/sections/HeroSection";
import AboutSection from "../../components/sections/AboutSection";
import SearchPathsSection from "../../components/sections/SearchPathsSection";
import HighlightsSection from "../../components/sections/HighlightsSection";
import IntakeWindowsSection from "../../components/sections/IntakeWindowsSection";
import ClosingSection from "../../components/sections/ClosingSection";

const HomePage = () => {
  const { hash } = useLocation();
  const [activeRoute, setActiveRoute] = useState<SearchRoute>("specialty");
  const [query, setQuery] = useState("");
  const [result, setResult] = useState("");

  const selectRoute = (route: SearchRoute) => {
    setActiveRoute(route);
    setQuery("");
    setResult("");
  };

  useEffect(() => {
    if (!hash) return;
    const frame = requestAnimationFrame(() => {
      document.getElementById(decodeURIComponent(hash.slice(1)))?.scrollIntoView();
    });
    return () => cancelAnimationFrame(frame);
  }, [hash]);

  return (
    <main id="main">
      <HeroSection
        activeRoute={activeRoute}
        onRouteChange={selectRoute}
        query={query}
        onQueryChange={setQuery}
        result={result}
        onResultChange={setResult}
      />
      <HighlightsSection />
      <SearchPathsSection onSelectRoute={selectRoute} />
      <IntakeWindowsSection />
      <AboutSection />
      <ClosingSection />
    </main>
  )
}

export default HomePage
